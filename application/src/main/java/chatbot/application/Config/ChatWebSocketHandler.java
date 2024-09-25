package chatbot.application.Config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    // Set to store all connected sessions
    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Add new session to the set
        sessions.add(session);
        System.out.println("Connected: " + session.getId() + " Total sessions: " + sessions.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Broadcast the message to all connected clients
        synchronized (sessions) {
            System.out.println("Broadcasting message from session: " + session.getId() + " to " + sessions.size() + " clients");
            for (WebSocketSession webSocketSession : sessions) {
                if (webSocketSession.isOpen()) {
                    System.out.println("Sending message to session: " + webSocketSession.getId());
                    webSocketSession.sendMessage(new TextMessage("Client " + session.getId() + ": " + message.getPayload()));
                } else {
                    System.out.println("Session is closed: " + webSocketSession.getId());
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        // Remove session when client disconnects
        sessions.remove(session);
        System.out.println("Disconnected: " + session.getId() + " Remaining sessions: " + sessions.size());
    }
}
