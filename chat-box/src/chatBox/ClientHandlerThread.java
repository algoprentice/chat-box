/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Swapnil Jain - Refactored.
 */
public class ClientHandlerThread implements Runnable {
    
    ArrayList<ClientInfo> connectedClients;
    ClientInfo clientInfo;
    
    ClientHandlerThread(ClientInfo clientInfo, ArrayList<ClientInfo> connectedClients) throws IOException {
        this.clientInfo = clientInfo;
        this.connectedClients = connectedClients;
    }

    @Override
    public void run() {
        String message;
        BufferedReader reader = clientInfo.getReader();
        String clientName = clientInfo.getClientName();
        
        try {
            //Reading messages from client's input stream and telling other clients.
            while((message = reader.readLine()) != null) {
                //System.out.println(clientName + ": " + message);
                
                //If client types exit, he lefts; tell other clients of this, end that client connection.
                if(message.equals("exit")) {
                    System.out.println(clientName + ": Exit Message.");
                    tellEveryoneButMe("[" + GiveDate.now() + "] " + clientName + " left.");
                    break;
                }
                
                tellEveryoneButMe("[" + GiveDate.now() + "] " + clientName + ": " + message);
            }
            
            //System.out.println(GiveDate.now() + "Client Handler Out");
        } catch (IOException ex) {
           System.out.println("Error in telling everyone or reader.");
        } finally {
            //Closing socket and its streams.
            if(!clientInfo.isClosed()) {
                clientInfo.close();
                System.out.println(clientName + "'s connection closed successfully.");
            }
            else {
                System.out.println("ClientHandlerThread: Closed Already");
            }
        }
    }
    
    //Send message of one client to other clients excluding his self.
    public void tellEveryoneButMe(String message) {
        for (ClientInfo iteratorClientInfo : connectedClients) {
            if(!iteratorClientInfo.getClientName().equals(this.clientInfo.getClientName())) {
                PrintWriter writer = (PrintWriter) iteratorClientInfo.getWriter();
                writer.println(message);
                writer.flush();     //Removing this can create serious consequences.
            }
        }
    }
    
}
