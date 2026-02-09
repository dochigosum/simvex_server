package dochigosum.simvex.domain.project.exception;

import dochigosum.simvex.global.error.GlobalErrorCode;

public class ProjectException extends RuntimeException {

    private final GlobalErrorCode errorCode;

    public ProjectException(GlobalErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ProjectException(GlobalErrorCode errorCode, String detail) {
        super(errorCode.getMessage() + ": " + detail);
        this.errorCode = errorCode;
    }

    public GlobalErrorCode getErrorCode() {
        return errorCode;
    }

    public int getStatusCode() {  // @Override 제거
        return errorCode.getStatus().value();
    }
}