package dochigosum.simvex.domain.project.exception;

// 찾을 수 없을 때 예외
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Long projectId) {
        super("프로젝트를 찾을 수 없습니다 Id: " + projectId);
    }
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
