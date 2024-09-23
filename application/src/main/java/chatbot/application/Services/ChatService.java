package chatbot.application.Services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class ChatService {

    private WebSocket webSocket;

    public ChatService(String uri, WebSocket.Listener listener) {
        if (uri.startsWith("http://")) {
            uri = uri.replace("http://", "ws://");
        } else if (uri.startsWith("https://")) {
            uri = uri.replace("https://", "wss://");
        }

        HttpClient client = HttpClient.newHttpClient();
        try {
            webSocket = client.newWebSocketBuilder().buildAsync(URI.create(uri), listener).join();
        } catch (CompletionException e) {
            System.err.println("WebSocket connection failed: " + e.getMessage());
        }
    }

    public CompletableFuture<WebSocket> sendMessage(String message) {
        return webSocket.sendText(message, true);
    }

    public CompletableFuture<WebSocket> closeConnection(int statusCode, String reason) {
        return webSocket.sendClose(statusCode, reason);
    }
}
