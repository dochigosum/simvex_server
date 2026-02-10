package dochigosum.simvex.domain.drawing.presentation;

import dochigosum.simvex.domain.drawing.entity.Memo;
import dochigosum.simvex.domain.drawing.presentation.dto.request.MemoCreateRequest;
import dochigosum.simvex.domain.drawing.presentation.dto.response.MemoCreatedResponse;
import dochigosum.simvex.domain.drawing.presentation.dto.response.MemoResponse;
import dochigosum.simvex.domain.drawing.service.MemoService;
import dochigosum.simvex.global.error.GlobalErrorCode;

import dochigosum.simvex.global.error.exception.SimvexException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memo")
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<MemoCreatedResponse> create(@RequestBody MemoCreateRequest request) {
        if (request == null) {
            throw new SimvexException(GlobalErrorCode.INVALID_REQUEST);
        }

        Long createdId = memoService.create(request.drawingId(), request.detail());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MemoCreatedResponse(createdId));
    }

    @GetMapping("/{memoId}")
    public ResponseEntity<MemoResponse> get(@PathVariable Long memoId) {
        Memo memo = memoService.get(memoId);
        return ResponseEntity.ok(MemoResponse.from(memo));
    }

    @DeleteMapping("/{memoId}")
    public ResponseEntity<MemoResponse> delete(@PathVariable Long memoId) {
        Memo memo = memoService.delete(memoId);
        return ResponseEntity.ok(MemoResponse.from(memo));
    }
}
