package chatbot.application.Config;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import chatbot.application.Services.UserService;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final UserService userService;

    // Map to store chat rooms and their connected sessions
    private static final Map<String, Set<WebSocketSession>> chatRooms = Collections.synchronizedMap(new HashMap<>());

    public ChatWebSocketHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");
        chatRooms.computeIfAbsent(roomId, k -> new HashSet<>()).add(session);
        System.out.println("User joined room: " + roomId + " Total sessions in room: " + chatRooms.get(roomId).size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = (String) session.getAttributes().get("username");
        String roomId = (String) session.getAttributes().get("roomId");

        if (username != null && roomId != null) {
            String formattedMessage = username + ": " + message.getPayload();
            System.out.println("Sending message to room: " + roomId);

            // Broadcast the message to all connected clients in the same room
            synchronized (chatRooms.get(roomId)) {
                for (WebSocketSession webSocketSession : chatRooms.get(roomId)) {
                    if (webSocketSession.isOpen() && !webSocketSession.getId().equals(session.getId())) {
                        webSocketSession.sendMessage(new TextMessage(formattedMessage));
                    }
                }
            }
        } else {
            System.out.println("User is not authenticated or room is invalid, message not sent.");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");
        if (roomId != null) {
            chatRooms.get(roomId).remove(session);
            if (chatRooms.get(roomId).isEmpty()) {
                chatRooms.remove(roomId);
            }
            System.out.println("User left room: " + roomId + " Remaining sessions: " + chatRooms.getOrDefault(roomId, Collections.emptySet()).size());
        }
    }
}
