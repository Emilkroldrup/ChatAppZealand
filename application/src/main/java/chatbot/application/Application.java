package chatbot.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import chatbot.application.Config.ChatWebSocketHandler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ChatWebSocketHandler chatWebSocketHandler() {
        return new ChatWebSocketHandler();
    }

    // Ensure WebSocket connection only after Spring Boot app is ready :)
    @EventListener(ApplicationReadyEvent.class)
    public void startWebSocketConnectionAfterBoot() {
        System.out.println("Spring Boot has fully started and WebSocket is ready.");
    }
}
