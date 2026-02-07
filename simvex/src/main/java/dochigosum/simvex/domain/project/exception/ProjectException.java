package dochigosum.simvex.domain.project.exception;

// 도메인 공통 예외
public class ProjectException extends RuntimeException {
    public ProjectException(String message) {
        super(message);
    }
    public ProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
