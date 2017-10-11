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
import javax.swing.JOptionPane;

/**
 *
 * @author Swapnil Jain
 */
public class Client {

    String clientName;
    PrintWriter toServer;
    BufferedReader fromServer;
    Socket clientSocket;

    public void start() throws Exception {
        clientSocket = new Socket(ServerInfo.hostName, ServerInfo.port);

        clientName = JOptionPane.showInputDialog("Enter your name: ");
        toServer = new PrintWriter(clientSocket.getOutputStream());
        fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        toServer.write(clientName);
        toServer.close();
        System.out.println("Client Side; Connection Established");
    }

    public static void main(String[] args) throws Exception {
        new Client().start();
    }
}
