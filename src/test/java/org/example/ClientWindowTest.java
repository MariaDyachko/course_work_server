package org.example;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.example.client.ClientWindow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientWindowTest {

    @DisplayName("log file is ready to work")
    @Test
    void logTest() throws IOException {
        int a = 8;
        String test = "test";
        ClientWindow client = new ClientWindow();
        client.log(test);

        String fileName = "C://dir/History/History.txt";
        File file = new File(fileName);

        ReversedLinesFileReader reader = new ReversedLinesFileReader(file);
        String line =  reader.readLine();

        //assertThat(line.equals("test")).isTrue();

        if (line.equals(test)){
            assertEquals(1, 1);
        } else {
            assertEquals(1, 2);
        }

    }

}
