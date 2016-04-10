package bitcamp.nelsontsui.roombacontroller;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Nelnel33 on 4/9/16.
 * Run in regular java
 */
public class RoombaServer {

    //String hostName = "10.102.185.195";
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

    public static void main(String[] args){
        RoombaServer rs = new RoombaServer();
        rs.server();
    }

}
