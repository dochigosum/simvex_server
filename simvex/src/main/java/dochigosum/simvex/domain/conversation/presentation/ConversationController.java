package dochigosum.simvex.domain.conversation.presentation;

import dochigosum.simvex.domain.conversation.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversation")
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping("/summary")
    public ResponseEntity<?> getAllConversations(@RequestParam Long id) { //현재는 conversationId 인데, 이후에 drawingId로 변경
        return ResponseEntity.status(HttpStatus.OK)
                .body(conversationService.getMessage(id));
    }

    @PutMapping("/summary")
    public ResponseEntity<?> updateConversation(@RequestParam Long id, @RequestParam String message) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(conversationService.retouchMessage(id, message));
    }
}