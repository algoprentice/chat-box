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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Swapnil Jain
 */
public class ClientHandlerThread implements Runnable {
    
    ArrayList toClientStreams;
    Socket clientSocket;
    BufferedReader reader;
    String clientName;
    
    ClientHandlerThread(Socket clientSocket, ArrayList toClientStreams, String clientName) throws IOException {
        this.clientSocket = clientSocket;
        this.toClientStreams = toClientStreams;
        this.clientName = clientName;
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        String message;
        try {
            while((message = reader.readLine()) != null) {
                System.out.println(clientName + ": " + message);
                
                if(message.equals("exit")) {
                    System.out.println("Exit Message");
                    clientSocket.close();
                    break;
                }
                tellEveryone(clientName + ": " + message);
            }
            System.out.println("Client Handler Out");
        } catch (IOException ex) {
           System.out.println("Error in telling everyone or reader.");
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
    
}
