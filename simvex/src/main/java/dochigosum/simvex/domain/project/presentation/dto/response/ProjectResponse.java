package dochigosum.simvex.domain.project.presentation.dto.response;

import dochigosum.simvex.domain.project.entity.Project;

public record ProjectResponse(
        Long id,
        Long memberId,
        String name,
        String previewImgUrl
) {
    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getMemberId(),
                project.getName(),
                project.getPreviewImgUrl()
        );
    }
}