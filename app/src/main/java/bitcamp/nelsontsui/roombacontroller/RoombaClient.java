package bitcamp.nelsontsui.roombacontroller;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Created by Nelnel33 on 4/9/16.
 */
public class RoombaClient extends AsyncTask<Void,Void,Void> {
    String hostName = "10.102.185.195";
    int portNumber = 4444;
    boolean fromUser[];

    public static final int FORWARD = 0;
    public static final int BACK = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;


    public RoombaClient(){
        fromUser = new boolean[4];
        resetFromUser();
        Log.v("Initialize Client", "init client");
        //this.execute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        client();
        return null;
    }

    public void setFromUser(int orientation, boolean toSend){
        this.fromUser[orientation] = toSend;
    }

    public void resetFromUser(){
        for(int i=0;i<fromUser.length;i++){
            fromUser[i] = false;
        }
    }

    public void client(){

        Log.v("Client.start ", "Starting client");

        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            Log.v("Communication Done", "Connected to Server");
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            //String fromUser;

            //fromUser = stdIn.readLine();

            while (true) {
                //System.out.println("Server: " + fromServer);
                //if (fromServer.equals("Bye."))
                //    break;

                //if(fromUser != null && fromUser.equals("quit")){
                //    break;
                //}

                //fromUser = stdIn.readLine();
                if (fromUser != null) {
                    //Log.v("Client: ", fromUser);
                    if(fromUser[FORWARD]){
                        out.println("FORWARD");
                    }
                    if(fromUser[BACK]){
                        out.println("BACK");
                    }
                    if(fromUser[LEFT]){
                        out.println("LEFT");
                    }
                    if(fromUser[RIGHT]){
                        out.println("RIGHT");
                    }
                    //out.flush();
                }
            }
            //if (fromUser != null) {
            //    System.out.println("Client: " + fromUser);
            //    out.println(fromUser);
            //}
        } catch (UnknownHostException e) {
            Log.v("UnknownHostException ", "Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            Log.v("IOException ", "Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
