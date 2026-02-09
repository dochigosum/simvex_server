package dochigosum.simvex.domain.drawing.presentation.dto.response;

import dochigosum.simvex.domain.drawing.entity.DrawingPart;

public record PartExplainResponse(
        Long partId,
        String partName,
        String partDetail
) {
    public static PartExplainResponse from(DrawingPart part) {
        return new PartExplainResponse(
                part.getId(),
                part.getName(),
                part.getFileName()
        );
    }
}
