package chatapp.application.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import chatapp.domain.model.Message;

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

            // Notify the server when a new client connects
            listener.onMessageReceived(new Message(clientName + " has connected!", "Server"));

            while ((message = input.readLine()) != null) {
                // Send message to the server (clientName is the sender)
                listener.onMessageReceived(new Message(message, clientName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Send a message to this client
    public void sendMessage(String message) throws IOException {
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        output.println(message);
    }

    // Get the client name
    public String getClientName() {
        return clientName;
    }

}