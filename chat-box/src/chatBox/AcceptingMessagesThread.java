/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Swapnil Jain - Refactored.
 */
public class AcceptingMessagesThread implements Runnable {
    BufferedReader fromServer;
    
    //Constructor: Initialize client input stream.
    AcceptingMessagesThread(ClientInfo clientInfo) throws IOException {
        fromServer = clientInfo.getReader();
    }
    
    //Thread run() method that Read messages from server; 
    @Override
    public void run() {
        String message;
        try {
            //Read until fromServer stream terminates.
            while((message = fromServer.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException ex) {
            System.out.println("AcceptingMsgsThrd: readline() error.");
            Logger.getLogger(AcceptingMessagesThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
