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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Swapnil Jain
 */
public class AcceptingMessagesThread implements Runnable {
    BufferedReader fromServer;
    Socket clientSocket;
    DateFormat dateFormat;
    Date date;
    
    AcceptingMessagesThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        dateFormat = new SimpleDateFormat("HH:mm:ss");
	date = new Date();
	 //2016/11/16 12:08:43
    }

    @Override
    public void run() {
        String message;
        try {
            while((message = fromServer.readLine()) != null) {
                System.out.println("From Server: " + message);
            }
            System.out.println(dateFormat.format(date));
        } catch (IOException ex) {
            Logger.getLogger(AcceptingMessagesThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(dateFormat.format(date) + "Out Accepting");
    }
}
