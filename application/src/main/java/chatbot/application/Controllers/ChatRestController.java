package chatbot.application.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import chatbot.application.Entities.Chat;
import chatbot.application.Services.ChatService;

@RestController
public class ChatRestController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/rooms")
    public List<Chat> getRooms() {
        return chatService.findAll();
    }
}
