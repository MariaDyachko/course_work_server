package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;

import org.example.network.*;

public class ChatServer implements TCPConnectionListener{

    public static final Integer LOCALHOST_PORT = 8080;

    public static void main(String[] args) {
        new ChatServer();
    }

    //private final ArrayList<TCPConnection> connections = new ArrayList<>();
    public static final ArrayList<TCPConnection> connections = new ArrayList<>();

    public ChatServer() {

        int port = 8080;
        try {
            String fileName = "Settings.txt";
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            port = Integer.parseInt(br.readLine());

            br.close();
        } catch (IOException e) {
            System.out.println("Connection exception: " + e);
        }

        System.out.println("Server running...");
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while(true) {
                try{
                    new TCPConnection(this, serverSocket.accept()); //core
                }catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sentToAllConnections("Client connected: " + tcpConnection);
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        sentToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sentToAllConnections("Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sentToAllConnections(String value) {
        System.out.println(value);
        for (int i = 0; i < connections.size(); i++) {
            connections.get(i).sendString(value);
        }
    }
}
