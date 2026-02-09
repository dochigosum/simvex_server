package dochigosum.simvex.domain.drawing.presentation.dto.response;

import dochigosum.simvex.domain.drawing.entity.Memo;

public record MemoResponse(
        Long memoId,
        Long drawingId,
        String detail
) {
    public static MemoResponse from(Memo memo) {
        return new MemoResponse(memo.getId(), memo.getDrawingId(), memo.getDetail());
    }
}
