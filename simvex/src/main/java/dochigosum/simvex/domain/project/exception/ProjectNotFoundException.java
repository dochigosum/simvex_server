package dochigosum.simvex.domain.project.exception;

import dochigosum.simvex.global.error.GlobalErrorCode;

// 찾을 수 없을 때 예외
public class ProjectNotFoundException extends RuntimeException {

    private final String project_name;

    public ProjectNotFoundException(final String project_name) {
        super(GlobalErrorCode.PROJECT_NOT_FOUND.getMessage() + ": " + project_name);
        this.project_name = project_name;
    }

    public String getproject_name() {
        return project_name;
    }
}
