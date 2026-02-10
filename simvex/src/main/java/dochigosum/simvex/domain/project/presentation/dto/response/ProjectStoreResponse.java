package dochigosum.simvex.domain.project.presentation.dto.response;

import java.time.LocalDateTime;

public record ProjectStoreResponse(
        String message,
        LocalDateTime savedAt
) {
    public static ProjectStoreResponse of(String projectName) {
        String message = "Project '" + projectName + "' saved successfully";

        return new ProjectStoreResponse(
                message,
                LocalDateTime.now()
        );
    }
}