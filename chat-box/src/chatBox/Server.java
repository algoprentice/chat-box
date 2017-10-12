/*
2-Client Chat App
1. Ask for user name as alias.
2. Gui window, where client msgs at right and another's msgs at left.
 */
package chatBox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Swapnil Jain
 */
public class Server {

    ServerSocket serverSocket;
    ArrayList toClientStreams;

    public void start() throws Exception {
        serverSocket = new ServerSocket(ServerInfo.port);
        toClientStreams = new ArrayList();
        
        System.out.println("Server Running...");
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            
            String clientName = promptInfo(clientSocket);
            
            Thread T = new Thread(new ClientHandlerThread(clientSocket, toClientStreams, clientName));
            T.start();
            
            System.out.println("Server Side; Connection Established");
        }
    }
    
    public String promptInfo(Socket clientSocket) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        toClientStreams.add(writer);
        
        String clientName = reader.readLine();
        return clientName;
    }

    public static void main(String[] args) throws Exception {
        new Server().start();
    }
}
