package chatbot.application.Config;

import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

// CLient sided sockets
public class CustomSocketListener implements WebSocket.Listener {


    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("WebSocket connection opened. ");
        webSocket.request(1); // Should start listening for messages
        //Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println("Received message: " + data);
        webSocket.request(1); // Request to keep receiving messages
        return CompletableFuture.completedFuture(null);
    }
    
    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed with status " + statusCode + ", reason: " + reason);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("WebSocket error: " + error.getMessage());
    }

    @Override
    public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
        System.out.println("Ping received. ");
        return Listener.super.onPing(webSocket, message);
    }

    @Override
    public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
        System.out.println("Pong received. ");
        return Listener.super.onPong(webSocket, message);
    }

    
    
}
