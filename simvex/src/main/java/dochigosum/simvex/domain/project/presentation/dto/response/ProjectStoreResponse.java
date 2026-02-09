package dochigosum.simvex.domain.project.presentation.dto.response;

import java.time.LocalDateTime;

public record ProjectStoreResponse(
        String message,
        LocalDateTime savedAt
) {
    public static ProjectStoreResponse of(String projectName) {
        return new ProjectStoreResponse(
                String.format("Project '%s' saved successfully", projectName),
                LocalDateTime.now()
        );
    }
}