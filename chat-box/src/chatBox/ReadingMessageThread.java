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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Swapnil Jain
 */
public class ReadingMessageThread implements Runnable {
    PrintWriter toServer;
    Socket clientSocket;
    
    ReadingMessageThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        toServer = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        String message;
        try {
            Scanner scan = new Scanner(System.in);
            while(true) {
                message = scan.nextLine();
                
                if("exit".equals(message)) {
                    toServer.close();
                    break;
                }
                
                toServer.println(message);
                toServer.flush();
            }
        } catch (Exception ex) {
            Logger.getLogger(ReadingMessageThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
