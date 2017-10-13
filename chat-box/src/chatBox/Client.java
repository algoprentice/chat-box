/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatBox;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Swapnil Jain
 */
public class Client {
    Socket clientSocket;

    public void start() throws Exception {
        clientSocket = new Socket(ServerInfo.hostName, ServerInfo.port);
        
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String clientName = scan.nextLine();
        
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        writer.println(clientName);
        writer.flush(); //Removing this can create serious consequences...
        
        System.out.println("Client Side; Connection Established");
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.start();
        
        new Thread(new AcceptingMessagesThread(client.clientSocket)).start();
        new Thread(new ReadingMessageThread(client.clientSocket)).start();
    }
}
