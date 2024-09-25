package chatbot.application.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chatbot.application.Entities.Chat;
import chatbot.application.Repositories.ChatRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat createChat(String chatName) {
        Chat chat = new Chat();
        chat.setChatName(chatName);
        return chatRepository.save(chat);
    }

    public Optional<Chat> findById(Long id) {
        return chatRepository.findById(id);
    }

    public Optional<Chat> findByChatName(String chatName) {
        return chatRepository.findByChatName(chatName);
    }

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }
}
