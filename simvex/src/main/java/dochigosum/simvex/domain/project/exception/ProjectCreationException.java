package dochigosum.simvex.domain.project.exception;

// 생성 실패 예외
public class ProjectCreationException extends RuntimeException {
    public ProjectCreationException(String message) {
        super(message);
    }
    public ProjectCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
