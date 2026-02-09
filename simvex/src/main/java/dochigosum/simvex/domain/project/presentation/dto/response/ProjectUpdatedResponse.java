package dochigosum.simvex.domain.project.presentation.dto.response;

import dochigosum.simvex.domain.project.entity.Project;

import java.time.LocalDateTime;

public record ProjectUpdatedResponse(
        Long id,
        Long userId,
        String name,
        String previewImgUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProjectUpdatedResponse from(Project project) {
        return new ProjectUpdatedResponse(
                project.getId(),
                project.getUserId(),
                project.getName(),
                project.getPreviewImgUrl(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
