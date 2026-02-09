package dochigosum.simvex.domain.template.exception;

import dochigosum.simvex.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum TemplateErrorCode implements ErrorCode {

    // 400
    QUERY_BAD_REQUEST(HttpStatus.BAD_REQUEST, "TMP_400", "검색어가 필요합니다."),

    // 404
    TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, "TMP_404", "리소스를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    TemplateErrorCode(HttpStatus status, String code, String message) {
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
