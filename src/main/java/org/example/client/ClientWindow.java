package org.example.client;

import org.example.network.TCPConnection;
import org.example.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {

    private static final String IP_ADDR = "127.0.0.1";
    //private static final int PORT = 8080;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fieldNickname = new JTextField("no name");
    private final JTextField fieldInput = new JTextField();
    private TCPConnection connection;

    public ClientWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        log.setEditable(false);
        log.setLineWrap(true);
        fieldInput.addActionListener(this);
        add(log, BorderLayout.CENTER);
        add(fieldInput, BorderLayout.SOUTH);
        add(fieldNickname, BorderLayout.NORTH);

        setVisible(true);

        int port = 8080;
        try {
        String fileName = "C://dir/Settings/Settings.txt";
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        port = Integer.parseInt(br.readLine());

        br.close();
        } catch (IOException e) {
            printMsg("Connection exception: " + e);
        }

        try {
            connection = new TCPConnection(this, IP_ADDR, port);//PORT);
        } catch (IOException e) {
            printMsg("Connection exception: " + e);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = fieldInput.getText();
        if (msg.equals("")){
            return;
        }
        fieldInput.setText(null);
        Date timeNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss");
        connection.sendString(formatForDateNow.format(timeNow) + " " + fieldNickname.getText() + ": " + msg);
    }


    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMsg(value);
        log(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " + e);
    }

    private synchronized void printMsg(String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    public synchronized void log(String value){
        String filePath = "C://dir/History/History.txt";
        String text = value + "\n";

        try {
            Files.write(Paths.get(filePath), text.getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

}

