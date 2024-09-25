package chatbot.application.Services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.springframework.beans.factory.annotation.Autowired;

import chatbot.application.Entities.Chat;
import chatbot.application.Entities.Message;
import chatbot.application.Entities.User;
import chatbot.application.Repositories.ChatRepository;

public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

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

    public Chat createChat(String chatName, List<User> participants, List<Message> messages) {
        Chat chat = new Chat();
        chat.setChatName(chatName);
        chat.setParticipants(participants);
        chat.setMessages(messages);
    
        return chatRepository.save(chat);
    }

    public Optional<Chat> findById(Long id) {
        return chatRepository.findById(id);
    }

    public Optional<Chat> findByChatName(String chatName) {
        return chatRepository.findByChatName(chatName);
    }

    public Optional<Chat> findByParticipants(List<User> participants) {
        return chatRepository.findByParticipants(participants);
    }

    public Optional<Chat> findByMessages(List<Message> messages) {
        return chatRepository.findByMessages(messages);
    }


}
