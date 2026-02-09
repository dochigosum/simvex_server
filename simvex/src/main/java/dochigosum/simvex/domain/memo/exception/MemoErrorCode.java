package dochigosum.simvex.domain.memo.exception;

import dochigosum.simvex.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemoErrorCode implements ErrorCode {
    // 400
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "MEM_400", "잘못된 요청입니다."),

    // 404
    MEMO_NOT_FOUND(HttpStatus.NOT_FOUND, "MEM_404_01", "메모를 찾을 수 없습니다."),
    DRAWING_NOT_FOUND(HttpStatus.NOT_FOUND, "MEM_404_02", "드로잉을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    MemoErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
