package dochigosum.simvex.domain.memo.presentation.dto;

import dochigosum.simvex.domain.memo.entity.Memo;

public record MemoResponse(
        Long memoId,
        Long drawingId,
        String detail
) {
    public static MemoResponse from(Memo memo) {
        return new MemoResponse(memo.getId(), memo.getDrawingId(), memo.getDetail());
    }
}
