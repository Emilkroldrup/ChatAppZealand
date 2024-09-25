package chatbot.application.Services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chatbot.application.Entities.Chat;
import chatbot.application.Entities.Message;
import chatbot.application.Entities.User;
import chatbot.application.Repositories.MessageRepository;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(String content, LocalDateTime timestamp, User sender, Chat chat) {
        Message message = new Message();
        message.setContent(content);
        message.setTimestamp(timestamp);
        message.setSender(sender);
        message.setChat(chat);
        return messageRepository.save(message);
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public Optional<Message> findBySender(User sender) {
        return messageRepository.findBySender(sender);
    }

    public Optional<Message> findByTimestamp(LocalDateTime timestamp) {
        return messageRepository.findByTimestamp(timestamp);
    }

    public Optional<Message> findByChat(Chat chat) {
        return messageRepository.findByChat(chat);
    }

}

