package chatbot.application.Config;

import org.springframework.web.socket.WebSocketSession;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;

// Server sided sockets
@Component
public class CustomWebSocketHandler implements WebSocketHandler {

    Logger logger = Logger.getLogger(CustomWebSocketHandler.class.getName());

    public CustomWebSocketHandler() {
        
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connection established on session: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String msgString = (String) message.getPayload();
        logger.info(msgString);
        session.sendMessage(new TextMessage("Attempts to send..." + session +" - " + msgString));
        Thread.sleep(1000);
        session.sendMessage(new TextMessage("Completeded attempt. " + msgString));
        
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.info("Error occured: " + exception.getMessage() + " on session: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info(String.format("Connection closed on session: %s with status: %d", session.getId(), closeStatus.getCode()));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
