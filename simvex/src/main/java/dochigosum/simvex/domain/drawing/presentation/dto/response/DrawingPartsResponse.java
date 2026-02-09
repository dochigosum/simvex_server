package dochigosum.simvex.domain.drawing.presentation.dto.response;

import dochigosum.simvex.domain.drawing.entity.DrawingPart;

public record DrawingPartsResponse(
        Long partId,
        String partName,
        String partModelUrl
) {
    public static DrawingPartsResponse of(Long partId,String partName, String partModelUrl) {
        return new DrawingPartsResponse(partId, partName, partModelUrl);
    }

    public static DrawingPartsResponse from(DrawingPart part) {
        return new DrawingPartsResponse(
                part.getId(),
                part.getName(),
                part.getFileName()
        );
    }
}
