package chatbot.application.Config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import chatbot.application.Services.UserService;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final UserService userService;

    public ChatWebSocketHandler(UserService userService) {
        this.userService = userService;
    }

    // Set to store all connected sessions
    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Total sessions: " + sessions.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Retrieve the username stored by the handshake interceptor
        String username = (String) session.getAttributes().get("username");

        if (username != null) {
            System.out.println("User is authenticated: " + username);
            String formattedMessage = username + ": " + message.getPayload();

            // Broadcast the message to all connected clients
            synchronized (sessions) {
                for (WebSocketSession webSocketSession : sessions) {
                    if (webSocketSession.isOpen()) {
                        webSocketSession.sendMessage(new TextMessage(formattedMessage));
                    }
                }
            }
        } else {
            System.out.println("User is not authenticated, message not sent.");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Disconnected: " + session.getId() + " Remaining sessions: " + sessions.size());
    }
}
