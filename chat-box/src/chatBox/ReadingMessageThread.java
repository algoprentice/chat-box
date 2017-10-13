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
 * @author Swapnil Jain - Refactored.
 */
public class ReadingMessageThread implements Runnable {
    PrintWriter toServer;
    
    ReadingMessageThread(ClientInfo clientInfo) throws IOException {
        toServer = clientInfo.getWriter();
    }

    //Taking user input and sending to the server.
    @Override
    public void run() {
        String message;
        try {
            Scanner scan = new Scanner(System.in);
            while(true) {
                message = scan.nextLine();

                toServer.println(message);
                toServer.flush();
                
                //On "exit", break the loop; clientHandlerThread running on server will close the connection.
                if("exit".equals(message)) {
                    break;
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ReadingMessageThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
    }
}
