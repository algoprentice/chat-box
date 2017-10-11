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
    ArrayList clientNames, toClientStreams, fromClientStreams;

    public void start() throws Exception {
        serverSocket = new ServerSocket(ServerInfo.port);
        clientNames = new ArrayList();
        fromClientStreams = new ArrayList();
        toClientStreams = new ArrayList();
        
        System.out.println("Server Running...");
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            promptInfo(clientSocket);
            
            
            
            System.out.println("Server Side; Connection Established");
        }
    }
    
    public void tellEveryone(String message) {
        Iterator it = toClientStreams.iterator();
        while(it.hasNext()) {
            PrintWriter writer = (PrintWriter) it.next();
            writer.println(message);
            writer.flush();
        }
    }
    
    public void promptInfo(Socket clientSocket) throws Exception {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String clientName = reader.readLine();
        System.out.println("Server Side; " + clientName);

        clientNames.add(clientName);
        fromClientStreams.add(reader);
        toClientStreams.add(writer);
    }

    public static void main(String[] args) throws Exception {
        new Server().start();
    }
}
