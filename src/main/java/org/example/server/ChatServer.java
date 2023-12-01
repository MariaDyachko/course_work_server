package org.example.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.io.FileOutputStream;

import org.example.network.*;

public class ChatServer implements TCPConnectionListener{

    public static final Integer LOCALHOST_PORT = 8080;

    public static void main(String[] args) {
        createSettingsFile();
        new ChatServer();
    }

    //public ArrayList<TCPConnection> getConnections() {
    //    return connections;
    //}

    //private final ArrayList<TCPConnection> connections = new ArrayList<>();
    public static final ArrayList<TCPConnection> connections = new ArrayList<>();

    public ChatServer() {
        System.out.println("Server running...");
        try(ServerSocket serverSocket = new ServerSocket(LOCALHOST_PORT);) {
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

    private static void createSettingsFile(){
        File dir = new File("C://dir");
        dir.mkdir();
        File dirSettings = new File("C://dir/Settings");
        dirSettings.mkdir();
        System.out.println("roots created");

        File settings = new File(dirSettings, "Settings.txt");
        try {
            settings.createNewFile();
        } catch (IOException e) {
            System.out.println("файл с настройками не создан");
        }

        try (FileOutputStream fos = new FileOutputStream(settings)){
            String success = "" + LOCALHOST_PORT;
            fos.write(success.getBytes());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File dirHistory = new File("C://dir/History");
        dirHistory.mkdir();

        File history = new File(dirHistory, "History.txt");
        try {
            history.createNewFile();
        } catch (IOException e) {
            System.out.println("файл с настройками не создан");
        }
        System.out.println("files created");

    }
}
