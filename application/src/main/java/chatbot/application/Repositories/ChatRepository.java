package chatbot.application.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chatbot.application.Entities.Chat;
import chatbot.application.Entities.Message;
import chatbot.application.Entities.User;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>{
    Optional<Chat> findById(Long id);

    Optional<Chat> findByChatName(String chatName);

    Optional<Chat> findByParticipants(List<User> participants);

    Optional<Chat> findByMessages(List<Message> messages);
}
