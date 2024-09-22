package chatbot.application.Controllers;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import chatbot.application.Config.CustomSocketListener;
import chatbot.application.Services.ChatService;


@Controller
public class ChatController {

    private final ChatService chatService;
    
    private final List<String> chatHistory = new ArrayList<>();
    private final List<WebSocket> sockets = new ArrayList<>();
    
    private WebSocket webSocket;
    
    
    public ChatController(String uri) {
        this.chatService = new ChatService(uri, new CustomSocketListener());
    }


    public void sendMessage(String message) {
        chatService.sendMessage(message); // Sends message
    }
    
    public void closeConnection(int statusCode, String reason) {
        chatService.closeConnection(statusCode, reason); // Closes connection
    }

    @GetMapping("/chat") 
    public String getChatPage(Model model) {
        model.addAttribute("title", "WebSocket Chat");
        return "chat"; // Returns chat.html
    }

    @GetMapping("/stream")
    public WebSocket stream() {
        // Creates a new socket to create a connection
        return null;
        
    }
    
}
