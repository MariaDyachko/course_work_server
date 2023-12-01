package org.example;

import org.example.client.ClientWindow;
import org.example.network.TCPConnection;
import org.example.server.ChatServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatServerTest {

    @DisplayName("client connected")
    @Test
    void connectionTest() throws InterruptedException {
        //ChatServer chatServer = null; // = new Thread(() -> new ChatServer(););
        //new ClientWindow();
        //ArrayList<TCPConnection> connections = chatServer.getConnections();
        //assertEquals(connections.size(), 1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ChatServer chatServer = new ChatServer();
            }
        });
        thread.start();
        Thread.sleep(1000);
        new ClientWindow();
        Thread.sleep(1000);
        //ArrayList<TCPConnection> connections = chatServer.getConnections();
        assertEquals(ChatServer.connections.size(), 1);



    }

}
