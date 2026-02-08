package dochigosum.simvex.domain.memo.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public record MemoCreateRequest(
        Long drawingId,
        String detail
) {}
