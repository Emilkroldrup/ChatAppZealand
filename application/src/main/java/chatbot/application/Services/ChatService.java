package chatbot.application.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chatbot.application.Entities.Chat;
import chatbot.application.Entities.Message;
import chatbot.application.Entities.User;
import chatbot.application.Repositories.ChatRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat createChat(String chatName, List<User> participants, List<Message> messages) {
        Chat chat = new Chat();
        chat.setChatName(chatName);
        chat.setParticipants(participants);
        chat.setMessages(messages);
        return chatRepository.save(chat);
    }

    public Optional<Chat> findById(Long id) {
        return chatRepository.findById(id);
    }

    public Optional<Chat> findByChatName(String chatName) {
        return chatRepository.findByChatName(chatName);
    }

    public Optional<Chat> findByParticipants(List<User> participants) {
        return chatRepository.findByParticipants(participants);
    }

    public Optional<Chat> findByMessages(List<Message> messages) {
        return chatRepository.findByMessages(messages);
    }

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }
}
