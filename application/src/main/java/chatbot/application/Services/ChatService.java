package chatbot.application.Services;

import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.net.URI;

public class ChatService {

    private WebSocket webSocket;

    // Constructor to initialize WebSocket connection
    public ChatService(String uri, WebSocket.Listener listener) {
        HttpClient client = HttpClient.newHttpClient();
        webSocket = client.newWebSocketBuilder().buildAsync(URI.create(uri), listener).join();
    }

    // Method to send message
    public CompletableFuture<WebSocket> sendMessage(String message) {
        return webSocket.sendText(message, true);
    }

    // Method to close connection
    public CompletableFuture<WebSocket> closeConnection(int statusCode, String reason) {
        return webSocket.sendClose(statusCode, reason);
    }

}
