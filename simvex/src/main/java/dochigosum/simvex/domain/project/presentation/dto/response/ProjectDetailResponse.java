package dochigosum.simvex.domain.project.presentation.dto.response;

import dochigosum.simvex.domain.project.entity.Project;

import java.time.LocalDateTime;

public record ProjectDetailResponse(
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProjectDetailResponse from(Project project) {
        return new ProjectDetailResponse(
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}