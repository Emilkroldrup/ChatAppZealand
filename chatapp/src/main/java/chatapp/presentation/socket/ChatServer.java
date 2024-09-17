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
    private static Set<SocketHandler> clientHandlers = new HashSet<>(); // List of connected clients

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("New client Connected!");

        while (true) { 
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client Connected!");

            SocketHandler handler = new SocketHandler(clientSocket, "Client" + clientHandlers.size(), new ChatServer());
            clientHandlers.add(handler);

            new Thread(handler).start();
        }
    }

    @Override
    public void onMessageReceived (Message message) {
        System.out.println("Received " + message);
        broadcastMessage(message);
    }

    private void broadcastMessage(Message message) {
        for (SocketHandler handler : clientHandlers) {
            try {
                handler.sendMessage(message.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
