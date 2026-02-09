package dochigosum.simvex.domain.project.exception;

import dochigosum.simvex.global.error.GlobalErrorCode;

public class ProjectNameDuplicateException extends RuntimeException {

    private final String project_name;

    public ProjectNameDuplicateException(String project_name) {
        super(GlobalErrorCode.PROJECT_NAME_DUPLICATE.getMessage() + ": " + project_name);
        this.project_name = project_name;
    }

    public String getProjectName() {
        return project_name;
    }
}
