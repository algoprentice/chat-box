/*
2-Client Chat App
1. Ask for user name as alias.
 */

package chatBox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Swapnil Jain - Refactored.
 */
public class Server {
    
    ServerSocket serverSocket;
    ArrayList<ClientInfo > connectedClients;

    public void start() throws Exception {
        
        //Started Server on concerned port.
        serverSocket = new ServerSocket(ServerInfo.port);
        connectedClients = new ArrayList<> ();
        
        System.out.println("Server Running...");
        
        //Server in loop; accept client and prompt its name; also start clientHandler Thread
        while (true) {
            Socket clientSocket = serverSocket.accept();
            
            //Initializing clientInfo object and adding to connectedClients list.
            ClientInfo clientInfo = new ClientInfo(clientSocket);
            connectedClients.add(clientInfo);
            
            //Initializing ClientHandlerThread.
            ClientHandlerThread clientHandlerThread = new ClientHandlerThread(clientInfo, connectedClients);
            
            //Starting client handler thread.
            Thread T = new Thread(clientHandlerThread);
            T.start();
            
            System.out.println("Server Side: Connection Established with " + "\"" + clientInfo.getClientName() + "\"");
        }
    }

    //Starting Server...
    public static void main(String[] args) throws Exception {
        new Server().start();
    }
}
