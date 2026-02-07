package dochigosum.simvex.domain.coversation.repository;

import dochigosum.simvex.domain.coversation.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
