/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exampleserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Nelnel33
 */
public class ExampleServer {
    
   String hostName = "10.102.185.195";
   int portNumber = 4444;
    
   public void server(){
       try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
           
           System.out.println("Server Connected to client");
        
            String inputLine;
            
             while ((inputLine = in.readLine()) != null) {
                System.out.println(""+inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
   }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ExampleServer es = new ExampleServer();
        es.server();
    }
    
}
