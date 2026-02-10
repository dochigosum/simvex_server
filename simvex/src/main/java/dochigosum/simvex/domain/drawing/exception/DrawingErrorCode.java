package dochigosum.simvex.domain.drawing.exception;

import dochigosum.simvex.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum DrawingErrorCode implements ErrorCode {

    // 400
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "DRW_400", "잘못된 요청입니다."),

    // 404
    NOT_FOUND(HttpStatus.NOT_FOUND, "DRW_404", "리소스를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    DrawingErrorCode(HttpStatus status, String code, String message) {
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
