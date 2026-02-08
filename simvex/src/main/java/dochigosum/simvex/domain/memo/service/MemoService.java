package dochigosum.simvex.domain.memo.service;

import dochigosum.simvex.domain.drawing.repository.DrawingRepository;
import dochigosum.simvex.domain.memo.entity.Memo;
import dochigosum.simvex.domain.memo.exception.DrawingNotFoundException;
import dochigosum.simvex.domain.memo.exception.MemoBadRequestException;
import dochigosum.simvex.domain.memo.exception.MemoErrorCode;
import dochigosum.simvex.domain.memo.exception.MemoNotFoundException;
import dochigosum.simvex.domain.memo.repository.MemoRepository;
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
        if (drawingId == null) {
            throw new SimvexException(GlobalErrorCode.INVALID_REQUEST);
        }
        if (detail == null || detail.trim().isEmpty()) {
            throw new SimvexException(GlobalErrorCode.INVALID_REQUEST);
        }

        if (!drawingRepository.existsById(drawingId)) {
            throw new DrawingNotFoundException(drawingId);
        }

        Memo memo = memoRepository.save(new Memo(drawingId, detail));
        return memo.getId();
    }

    public Memo get(Long memoId) {
        if (memoId == null) {
            throw new MemoBadRequestException("memoId is required.");
        }
        return memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoNotFoundException(memoId));
    }

    @Transactional
    public Memo delete(Long memoId) {
        Memo memo = get(memoId);
        memoRepository.delete(memo);
        return memo;
    }
}
