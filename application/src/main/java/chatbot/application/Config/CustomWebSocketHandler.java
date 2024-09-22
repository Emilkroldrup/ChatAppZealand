package chatbot.application.Config;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;

public class CustomWebSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new ArrayList<>();

    @SuppressWarnings("null")
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session); // Removes the session when connection is closed
        System.out.println("Connection closed: " + session.getId());
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        sessions.add(session); // Adds the session to the active sessions
        session.sendMessage(new TextMessage("Connected to the chat!"));
    }

    // There is a method to handle all kinds of messages if needed in the Interface

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        String incomingMessage = message.getPayload();
        System.out.println("Received message: " + incomingMessage);

        // Broadcasts the message to all connected sessions
        for (WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage("User: " + incomingMessage));
        }
    }

}
