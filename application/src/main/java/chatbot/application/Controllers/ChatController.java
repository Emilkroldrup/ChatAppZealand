package chatbot.application.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/chat")
    public String getChatPage(Model model) {
        model.addAttribute("title", "WebSocket Chat");
        return "chat";
    }
}
