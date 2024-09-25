package chatbot.application.Config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import chatbot.application.Entities.User;
import chatbot.application.Services.UserService;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final UserService userService;
    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    public ChatWebSocketHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Connected: " + session.getId() + " Total sessions: " + sessions.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Get username from the WebSocket session attributes
        String username = (String) session.getAttributes().get("username");

        if (username != null) {
            System.out.println("Authenticated username: " + username);

            User currentUser = userService.findByUsername(username).orElse(null);
            if (currentUser != null) {
                System.out.println("User is authenticated: " + currentUser.getUsername());
            } else {
                System.out.println("Authenticated user not found in the database.");
            }
        } else {
            System.out.println("User is not authenticated.");
        }

        // Broadcast the message to all connected clients except the sender
        synchronized (sessions) {
            for (WebSocketSession webSocketSession : sessions) {
                if (webSocketSession.isOpen() && !webSocketSession.getId().equals(session.getId())) {
                    webSocketSession.sendMessage(new TextMessage("Client " + session.getId() + ": " + message.getPayload()));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Disconnected: " + session.getId() + " Remaining sessions: " + sessions.size());
    }
}
