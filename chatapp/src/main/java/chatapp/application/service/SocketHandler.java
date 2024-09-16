package chatapp.application.service;

import chatapp.domain.model.Message;

import java.io.*;
import java.net.Socket;

public class SocketHandler implements Runnable {
    private Socket socket;
    private String clientName;
    private SocketMessageListener listener;

    public SocketHandler(Socket socket, String clientName, SocketMessageListener listener) {
        this.socket = socket;
        this.clientName = clientName;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while ((message = input.readLine()) != null) {
                // Notify the listener (e.g., server or client) when a message is received
                listener.onMessageReceived(new Message(message, clientName));
            }
        } catch (IOException e) {
            // Log the exception
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        SocketHelper.sendMessage(socket, message);
    }
}