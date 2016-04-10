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
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Nelnel33
 */
public class ExampleClient {
    
    String hostName = "10.105.199.182";
    int portNumber = 4444;
    
    public void Client(){
        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            System.out.println("Client connected");
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            
            fromUser = stdIn.readLine();
            
            while (true) {
                //System.out.println("Server: " + fromServer);
                //if (fromServer.equals("Bye."))
                //    break;
                
                if(fromUser != null && fromUser.equals("quit")){
                    break;
                }
                
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                    out.flush();
                }
            }
            //if (fromUser != null) {
            //    System.out.println("Client: " + fromUser);
            //    out.println(fromUser);
            //}
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
    
    public static void main(String[] args){
        ExampleClient ec = new ExampleClient();
        ec.Client();
    }
}

