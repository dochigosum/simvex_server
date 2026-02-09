package dochigosum.simvex.domain.drawing.presentation.dto.request;

public record MemoCreateRequest(
        Long drawingId,
        String detail
) {}
