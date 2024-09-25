package chatbot.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import chatbot.application.Config.ChatWebSocketHandler;
import chatbot.application.Services.UserService;

@SpringBootApplication
public class Application {

    private final UserService userService;

    public Application(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ChatWebSocketHandler chatWebSocketHandler() {
        return new ChatWebSocketHandler(userService); // Pass the userService to the handler
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startWebSocketConnectionAfterBoot() {
        System.out.println("Spring Boot has fully started and WebSocket is ready.");
    }
}
