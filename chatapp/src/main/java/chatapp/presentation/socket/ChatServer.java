package chatapp.presentation.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import chatapp.application.service.SocketHandler;
import chatapp.application.service.SocketMessageListener;
import chatapp.domain.model.Message;

public class ChatServer implements SocketMessageListener {
    private static Set<SocketHandler> clientHandlers = new HashSet<>();  // Track connected clients

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);  // Listen on port 12345
        System.out.println("Server is running...");

        while (true) {
            Socket clientSocket = serverSocket.accept();  // Accept a new client connection
            System.out.println("New client connected!");

            // Create a new handler for the client and add it to the set of handlers
            SocketHandler handler = new SocketHandler(clientSocket, "Client" + clientHandlers.size(), new ChatServer());
            clientHandlers.add(handler);

            // Start a new thread to handle the client's messages
            new Thread(handler).start();
        }
    }

    // This method is called whenever a client sends a message
    @Override
    public void onMessageReceived(Message message) {
        System.out.println("Received: " + message);  // Display message on the server console
        broadcastMessage(message);  // Send the message to all clients
    }

    // Send a message to all connected clients, including server messages
    private void broadcastMessage(Message message) {
        for (SocketHandler handler : clientHandlers) {
            try {
                // Send the message to each client
                handler.sendMessage(message.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}