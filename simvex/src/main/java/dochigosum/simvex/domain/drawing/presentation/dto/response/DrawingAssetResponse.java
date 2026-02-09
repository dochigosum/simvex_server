package dochigosum.simvex.domain.drawing.presentation.dto.response;

import dochigosum.simvex.domain.drawing.entity.DrawingPart;

public record DrawingAssetResponse(
        Long partId,
        String partName,
        String partModelUrl
) {
    public static DrawingAssetResponse of(DrawingPart part, String url) {
        return new DrawingAssetResponse(
                part.getId(),
                part.getName(),
                url
        );
    }
}