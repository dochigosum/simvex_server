package dochigosum.simvex.domain.project.presentation.dto.response;

import dochigosum.simvex.domain.project.entity.Project;

public record ProjectsResponse(
        Long id,
        String name,
        String previewImgUrl
) {
    public static ProjectsResponse from(Project project) {
        return new ProjectsResponse(
                project.getId(), project.getName(), project.getPreviewImgUrl()
        );
    }
}