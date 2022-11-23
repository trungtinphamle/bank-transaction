package server;

import server.models.ServerFunctions;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main (String [] arg) throws IOException{
        ServerSocket server = new ServerSocket(7777);
        System.out.println("Server sẵn sàng kết nối...");
        while (true){
            Socket socket = server.accept();
            new ServerFunctions(socket).start();
        }
    }
}
