package org.example;

import org.example.client.ClientWindow;
import org.example.network.TCPConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TCPConnectionTest {

    @DisplayName("server didn't start")
    @Test
    void ConnectionTest() throws IOException {
        var exception = assertThrows(IOException.class, () -> new TCPConnection(new ClientWindow(), "127.0.0.1", 8080));
    }
}
