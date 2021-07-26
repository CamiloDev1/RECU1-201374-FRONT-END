package sample.controller;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Thread implements Runnable {
    Socket socket;
    Controller client;
    DataInputStream input;

    public Thread(Socket socket, Controller client) {
        this.socket = socket;
        this.client = client;
    }
    @Override 
    public void run() {
        while (true) {
            try {
                input = new DataInputStream(socket.getInputStream());
                String message = input.readUTF();
                Platform.runLater(() -> {
                    client.men.appendText(message + "\n");
                });
            } catch (IOException ex) {
                System.out.println("****Error de servidor**** " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}
