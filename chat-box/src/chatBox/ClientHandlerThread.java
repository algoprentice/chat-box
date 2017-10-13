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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    DateFormat dateFormat;
    Date date;
    
    ClientHandlerThread(Socket clientSocket, ArrayList toClientStreams) throws IOException {
        this.clientSocket = clientSocket;
        this.toClientStreams = toClientStreams;
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        dateFormat = new SimpleDateFormat("HH:mm:ss");
	date = new Date();
    }

    @Override
    public void run() {
        String message;
        try {
            while((message = reader.readLine()) != null) {
                System.out.println(this.clientName + "Says: " + message);
                
                if(message.equals("exit")) {
                    System.out.println("Exit Message");
                    clientSocket.close();
                    tellEveryone(this.clientName + " left");
                    break;
                }
                
                tellEveryone(this.clientName + "Says: " + message);
            }
            System.out.println(dateFormat.format(date) + "Client Handler Out");
        } catch (IOException ex) {
           System.out.println("Error in telling everyone or reader.");
        } finally {
            try {
                if(!clientSocket.isClosed()) this.clientSocket.close();
                else System.out.println(dateFormat.format(date) + "CHT: Closed Already");
            } catch (IOException ex) {
                System.out.println("ClientHandlerThread: Error in closing");
            }
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
