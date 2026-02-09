package dochigosum.simvex.domain.template.presentation.dto.response;

import dochigosum.simvex.domain.template.entity.DrawingTemplate;

public record DrawingSearchResponse(
        Long id,
        String name,
        String imageUrl
) {
    public static DrawingSearchResponse from(DrawingTemplate template) {
        return new DrawingSearchResponse(
                template.getId(),
                template.getName(),
                template.getImageUrl()
        );
    }
}
