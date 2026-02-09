package dochigosum.simvex.domain.member.exception;

import dochigosum.simvex.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode {

    // 401
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "MBR_401", "이메일 또는 비밀번호가 올바르지 않습니다."),

    // 403
    EMAIL_NOT_VERIFIED(HttpStatus.FORBIDDEN, "MBR_403", "이메일 인증이 필요합니다."),

    // 404
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MBR_404_01", "존재하지 않는 이메일입니다."),
    VERIFICATION_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "MBR_404_02", "인증 코드가 존재하지 않습니다. 다시 요청해 주세요."),

    // 409
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "MBR_409", "이미 가입된 이메일입니다."),

    // 400
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "MBR_400", "인증 코드가 올바르지 않습니다."),

    // 410
    VERIFICATION_CODE_EXPIRED(HttpStatus.GONE, "MBR_410", "인증 코드가 만료되었습니다. 다시 요청해 주세요.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    MemberErrorCode(HttpStatus status, String code, String message) {
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
