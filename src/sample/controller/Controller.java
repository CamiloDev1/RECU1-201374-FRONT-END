package sample.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtInput;

    @FXML
    public TextArea men;

    DataOutputStream output = null;
    public static String host="localhost";
    public static int port=3001;
    @FXML
    public void conectar() {
        try {
            men.setEditable(false);
            //Socket que conecta al servidor
            Socket socket = new Socket(host,port);
            men.appendText("---CONECTADO---\n");
            output = new DataOutputStream(socket.getOutputStream());

            Thread task;
            task = new Thread(socket, this);
            java.lang.Thread thread = new java.lang.Thread(task);
            thread.start();
        } catch (IOException ex) {
            System.out.println(ex.toString() + '\n');
            men.appendText(ex.toString() + '\n');
        }
    }

        @FXML
        public void handle(ActionEvent e) {
        txtInput.setEditable(false);
            try {

                String username = txtName.getText().trim();
                String message = txtInput.getText().trim();

                if (username.length() == 0) {
                    username = "Unknown";
                }

                if (message.length() == 0) {
                    return;
                }

                output.writeUTF("[" + username + "]: " + message + "");
                output.flush();

                txtInput.clear();
            } catch (IOException ex) {
                System.err.println(ex);
            }

        }
}
