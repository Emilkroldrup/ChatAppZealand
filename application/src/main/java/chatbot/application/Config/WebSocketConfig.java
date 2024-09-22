package chatbot.application.Config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    private final CustomWebSocketHandler socketHandler;

    // Constructor to initialize socket handler
    public WebSocketConfig(CustomWebSocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @SuppressWarnings("null")
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CustomWebSocketHandler(), "/chat-websocket")
        .setAllowedOrigins("*");
        }


}
