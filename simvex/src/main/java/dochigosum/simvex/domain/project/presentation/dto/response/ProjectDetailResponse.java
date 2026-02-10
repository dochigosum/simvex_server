package dochigosum.simvex.domain.project.presentation.dto.response;

import dochigosum.simvex.domain.project.entity.Project;

import java.time.LocalDateTime;

public record ProjectDetailResponse(
        Long id,
        String name,
        String previewImgUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProjectDetailResponse from(Project project) {
        return new ProjectDetailResponse(
                project.getId(),
                project.getName(),
                project.getPreviewImgUrl(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}