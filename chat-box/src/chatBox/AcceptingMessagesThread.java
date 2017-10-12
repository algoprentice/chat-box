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
 * @author Swapnil Jain
 */
public class AcceptingMessagesThread implements Runnable {
    BufferedReader fromServer;
    Socket clientSocket;
    
    AcceptingMessagesThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        String message;
        try {
            while(!clientSocket.isClosed() && (message = fromServer.readLine()) != null) {
                System.out.println("From Server: " + message);
            }
        } catch (IOException ex) {
            Logger.getLogger(AcceptingMessagesThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Out Accepting");
    }
}
