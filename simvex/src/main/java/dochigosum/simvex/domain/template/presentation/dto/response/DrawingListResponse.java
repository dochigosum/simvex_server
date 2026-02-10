package dochigosum.simvex.domain.template.presentation.dto.response;

import dochigosum.simvex.domain.template.entity.DrawingTemplate;

public record DrawingListResponse(
        Long id,
        String name,
        String imageUrl
) {
    public static DrawingListResponse from(DrawingTemplate template) {
        return new DrawingListResponse(
                template.getId(),
                template.getName(),
                template.getPreviewImg()
        );
    }
}
