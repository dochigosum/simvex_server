package dochigosum.simvex.domain.coversation.service;

import dochigosum.simvex.domain.coversation.entity.Conversation;
import dochigosum.simvex.domain.coversation.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public String getMessage(Long conversationId) { //추후 drawingId 로 교체
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("conversation not found"));

        return conversation.getMessage();
    }

    public String retouchMessage(Long conversationId, String message) { //conversationId를 이후에 drawingId로 교체
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("conversation not found"));
        conversation.retouchMessage(message);
        return conversation.getMessage();
    }

}