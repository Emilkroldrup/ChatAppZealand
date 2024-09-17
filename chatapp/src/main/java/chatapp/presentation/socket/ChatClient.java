package chatapp.presentation.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import chatapp.application.service.SocketHandler;
import chatapp.application.service.SocketMessageListener;
import chatapp.domain.model.Message;

public class ChatClient implements SocketMessageListener {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);  // Connect to server on localhost and port 12345
            SocketHandler handler = new SocketHandler(socket, "Client", new ChatClient());

            // Start the handler to listen for messages from the server
            new Thread(handler).start();

            // Read user input and send it to the server
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                handler.sendMessage(userInput);  // Send user input to the server
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(Message message) {
        // Display the message received from the server
        System.out.println(message);
    }
}