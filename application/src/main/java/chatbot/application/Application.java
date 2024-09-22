package chatbot.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import chatbot.application.Config.CustomWebSocketHandler;
import chatbot.application.Controllers.ChatController;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CustomWebSocketHandler customWebSocketHandler() {
		return new CustomWebSocketHandler();
	}

	@Bean
	public ChatController chatController() {
		return new ChatController("http://localhost:8080/chat");
	}
}
