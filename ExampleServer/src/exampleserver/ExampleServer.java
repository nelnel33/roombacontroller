/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exampleserver;

import com.jcraft.jsch.*;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nelnel33
 * 
 */
public class ExampleServer {
    
   String hostName = "10.102.185.195";
   int portNumber = 4444;
    
   public void server() throws JSchException{
       try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
           
           System.out.println("Server Connected to client");
           
           Session session = null;
           Channel channel = null;

               File file = new File("/home/rishab/roombacontroller/roombacontroller/Roomba \"Client\"/info.txt");
               Scanner inf = new Scanner(file);
               String host = inf.nextLine().trim();
               String username = inf.nextLine().trim();
               String password = inf.nextLine().trim();
               JSch jsch = new JSch();
               session = jsch.getSession(username, host, 22);
               java.util.Properties config = new java.util.Properties();
               config.put("StrictHostKeyChecking", "no");
               session.setConfig(config);
               session.setPassword(password);
               session.connect();
               System.out.println("ssh connected");
               
               String command = "python textController.py";
               channel = session.openChannel("exec");
               ((ChannelExec) channel).setCommand(command);
               
               OutputStream stdout = channel.getOutputStream();
               
               channel.connect();
               Thread.sleep(2000);
               System.out.println("Initialized");
               
               PrintStream ssh_stdout = new PrintStream(stdout);
               
           
            String inputLine;
            int i = 1;
            while ((inputLine = in.readLine()) != null) {
               System.out.println(i + ". " + inputLine);
               i++;
               ssh_stdout.print(inputLine + "\n");
               ssh_stdout.flush();
               if (inputLine.equals("quit+\n")) 
               {
                   ssh_stdout.close();
                   stdout.close();
                   break;
               }
           }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        } catch (InterruptedException ex) {
           Logger.getLogger(ExampleServer.class.getName()).log(Level.SEVERE, null, ex);
       }

   }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try {
           ExampleServer es = new ExampleServer();
           es.server();
       } catch (JSchException ex) {
           Logger.getLogger(ExampleServer.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
}
