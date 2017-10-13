/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Swapnil Jain - Refactored.
 */
public class ClientInfo {
    private final String clientName;            //Alias of the client connected to this Socket.
    private final PrintWriter writer;           //Socket output stream, from client to server.
    private final Socket clientSocket;          //Socket 
    private final BufferedReader reader;        //Socket input stream, from server to client.
    
    //Constructor: Accept clientSOcket and it's name as args; initialze i/o streams.
    public ClientInfo(Socket clientSocket, String clientName) {
        this.clientName = clientName;
        this.clientSocket = clientSocket;
        
        PrintWriter tempWriter = null;
        BufferedReader tempReader = null;
        
        //initializing streams of the client's socket.
        try {
            tempWriter = new PrintWriter(clientSocket.getOutputStream());  
            tempReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            System.out.println("ClientInfo:Client's Constructor not initialized.");
            Logger.getLogger(ClientInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.writer = tempWriter;
        this.reader = tempReader;
    }
    
    //Constructor: Accept clientsocket as argument and read clientName through socket's input stream.
    public ClientInfo(Socket clientSocket) {
        PrintWriter tempWriter = null;
        BufferedReader tempReader = null;
        String tempClientName = null;
        Socket tempClientSocket = null;
        
        //Setting socket & its i/o streams.
        try {
            tempClientSocket = clientSocket;
            tempWriter = new PrintWriter(clientSocket.getOutputStream());  
            tempReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            System.out.println("ClientInfo: Constructor Not Initialized.");
            Logger.getLogger(ClientInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.clientSocket = tempClientSocket;
        this.writer = tempWriter;
        this.reader = tempReader;
        
        
        try {
            //Ask name of the client by reading through its input stream.
            tempClientName = askNameofClient();
        } catch (Exception ex) {
            System.out.println("ClientInfo: Name not initialized.");
            Logger.getLogger(ClientInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.clientName = tempClientName;
    }
    
    //Reading client's name from client input stream.
    public final String askNameofClient() throws Exception {
        return this.reader.readLine();
    }
    
    //Close everything, first streams and then finally socket.
    public void close() {
        try {
            this.writer.close();
            this.reader.close();
            this.clientSocket.close();
        } catch (IOException ex) {
            System.out.println("ClientInfo: Error in Closing Socket.");
            Logger.getLogger(ClientInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getClientName() {
        return clientName;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public BufferedReader getReader() {
        return reader;
    }
    
    //Check if socket is closed.
    boolean isClosed() {
        return clientSocket.isClosed();
    }
}
