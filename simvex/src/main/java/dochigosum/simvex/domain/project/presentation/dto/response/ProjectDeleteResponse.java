package dochigosum.simvex.domain.project.presentation.dto.response;

public record ProjectDeleteResponse(
        String message
) {
    public static ProjectDeleteResponse of(String projectName) {
        return new ProjectDeleteResponse(
                String.format("Project %s deleted Successfully", projectName)
        );
    }
}
