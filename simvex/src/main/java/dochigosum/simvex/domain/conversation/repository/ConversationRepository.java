package dochigosum.simvex.domain.conversation.repository;

import dochigosum.simvex.domain.conversation.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
