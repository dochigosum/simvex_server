package dochigosum.simvex.global.error.exception;

import dochigosum.simvex.global.error.ErrorCode;

// 서비스 전반에서 사용하는 커스텀 예외의 베이스 클래스
public class SimvexException extends RuntimeException {

    private final ErrorCode errorCode;

    public SimvexException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public SimvexException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
