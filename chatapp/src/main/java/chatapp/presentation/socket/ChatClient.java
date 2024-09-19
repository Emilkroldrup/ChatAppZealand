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

            while (true) {
                System.out.println("Choose message type: (1) Broadcast (2) Unicast");
                String choice = consoleInput.readLine();
                String sender = handler.getClientName();
                

                if ("1".equals(choice)) {
                    System.out.println("Enter your message:");
                    userInput = consoleInput.readLine();
                    Message message = new Message(userInput, sender);
                    handler.sendMessage(message.toString());  // Send broadcast message to the server
                } else if ("2".equals(choice)) {
                    System.out.println("Enter the target client's name:");
                    String targetClientName = consoleInput.readLine();
                    System.out.println("Enter your message:");
                    userInput = consoleInput.readLine();
                    Message message = new Message(targetClientName, userInput, sender);
                    message.setTargetClientName(targetClientName);
                    handler.sendMessage(message.toString());  // Send unicast message to the server
                } else {
                    System.out.println("Invalid choice. Please enter 1 for Broadcast or 2 for Unicast.");
                }
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