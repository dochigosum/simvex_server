package dochigosum.simvex.domain.drawing.service;

import dochigosum.simvex.domain.drawing.repository.DrawingRepository;
import dochigosum.simvex.domain.drawing.entity.Memo;
import dochigosum.simvex.domain.drawing.exception.MemoErrorCode;
import dochigosum.simvex.domain.drawing.repository.MemoRepository;
import dochigosum.simvex.global.error.GlobalErrorCode;
import dochigosum.simvex.global.error.exception.SimvexException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoService {

    private final MemoRepository memoRepository;
//    private final DrawingRepository drawingRepository;

    @Transactional
    public Long create(Long drawingId, String detail) {

        if (drawingId == null || detail == null || detail.trim().isEmpty()) {
            throw new SimvexException(GlobalErrorCode.INVALID_REQUEST);
        }

        if (!drawingRepository.existsById(drawingId)) {
            throw new SimvexException(MemoErrorCode.DRAWING_NOT_FOUND);
        }

        Memo memo = memoRepository.save(new Memo(drawingId, detail));
        return memo.getId();
    }

    public Memo get(Long memoId) {
        if (memoId == null) {
            throw new SimvexException(GlobalErrorCode.INVALID_REQUEST);
        }

        return memoRepository.findById(memoId)
                .orElseThrow(() -> new SimvexException(MemoErrorCode.MEMO_NOT_FOUND, "memoId=" + memoId));
    }

    @Transactional
    public Memo delete(Long memoId) {
        Memo memo = get(memoId);
        memoRepository.delete(memo);
        return memo;
    }
}
