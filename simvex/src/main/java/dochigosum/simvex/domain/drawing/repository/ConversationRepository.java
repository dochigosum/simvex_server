package dochigosum.simvex.domain.drawing.repository;

import dochigosum.simvex.domain.drawing.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
