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
    ClientInfo clientInfo;

    public void start() throws Exception {
        Socket clientSocket = new Socket(ServerInfo.hostName, ServerInfo.port);
        
        //Prompting Client'Name
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String clientName = scan.nextLine();
        
        //Initialing clientInfo.
        clientInfo = new ClientInfo(clientSocket, clientName);
        
        //Sending client's name to server via output stream.
        PrintWriter writer = clientInfo.getWriter();
        writer.println(clientName);
        writer.flush(); //Removing this can create serious consequences...
        
        System.out.println("Connection Established");
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.start();
        
        new Thread(new AcceptingMessagesThread(client.clientInfo)).start();
        new Thread(new ReadingMessageThread(client.clientInfo)).start();
    }
}
