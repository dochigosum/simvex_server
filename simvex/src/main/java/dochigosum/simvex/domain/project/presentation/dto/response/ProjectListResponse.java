package dochigosum.simvex.domain.project.presentation.dto.response;

import dochigosum.simvex.domain.project.entity.Project;
import java.time.LocalDateTime;
import java.util.List;

public record ProjectListResponse(
        List<ProjectInfo> projects
) {
    public record ProjectInfo(
            Long id,
            Long userId,
            String name,
            String previewImgUrl,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static ProjectInfo from(Project project) {
            return new ProjectInfo(
                    project.getId(),
                    project.getUserId(),
                    project.getName(),
                    project.getPreviewImgUrl(),
                    project.getCreatedAt(),
                    project.getUpdatedAt()
            );
        }
    }

    public static ProjectListResponse from(List<ProjectResponse> projects) {
        List<ProjectInfo> projectInfos = projects.stream()
                .map(ProjectInfo::from)
                .toList();
        return new ProjectListResponse(projectInfos);
    }
}
