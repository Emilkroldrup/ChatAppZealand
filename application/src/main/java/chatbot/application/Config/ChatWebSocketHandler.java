package chatbot.application.Config;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.io.File;
import java.io.FileOutputStream;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import chatbot.application.Entities.Message;
import chatbot.application.Services.UserService;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final UserService userService;

    // Map to store chat rooms and their connected sessions
    private static final Map<String, Set<WebSocketSession>> chatRooms = Collections.synchronizedMap(new HashMap<>());

    private static final Set<Message> chatHistory = new HashSet<>();

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
            
            // Broadcast the message to all connected clients in the same room,
            // excluding the sender (to avoid echoing back the same message)
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
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        String username = (String) session.getAttributes().get("username");
        String roomId = (String) session.getAttributes().get("roomId");

        if (username != null && roomId != null) {
            System.out.println("Binary message received from user: " + username + " in room: " + roomId);

            // Save the binary message to a file
            String filePath = "ChatAppZealand/application/src/main/resources/static/filefolder/" + System.currentTimeMillis() + ".bin";
            try {
                ByteBuffer payload = message.getPayload();
                byte[] byteArray = new byte[payload.remaining()];
                payload.get(byteArray);

                // Save the file
                File file = new File(filePath);
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    fileOutputStream.write(byteArray);
                }
                System.out.println("Binary data saved to: " + file.getAbsolutePath());

                // Create a download link message
                String downloadLink = "Click here to download the file: <a href='/filefolder/" + file.getName() + "' download>" + file.getName() + "</a>";

                // Broadcast the link to other users in the same room
                synchronized (chatRooms.get(roomId)) {
                    for (WebSocketSession webSocketSession : chatRooms.get(roomId)) {
                        if (webSocketSession.isOpen() && !webSocketSession.getId().equals(session.getId())) {
                            webSocketSession.sendMessage(new TextMessage(downloadLink));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User is not authenticated or room is invalid, binary message not sent.");
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
