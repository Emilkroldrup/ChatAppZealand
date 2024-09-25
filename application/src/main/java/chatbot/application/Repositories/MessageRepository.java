package chatbot.application.Repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chatbot.application.Entities.Chat;
import chatbot.application.Entities.Message;
import chatbot.application.Entities.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findById(Long id);

    Optional<Message> findBySender(User sender);

    Optional<Message> findByTimestamp(LocalDateTime timestamp);

    Optional<Message> findByChat(Chat chat);
}
