package dochigosum.simvex.domain.drawing.presentation.dto.response;

import dochigosum.simvex.domain.drawing.entity.DrawingPart;

public record DrawingPartsData(
        Long partId,
        Integer xCoordinate,
        Integer yCoordinate,
        Integer zCoordinate,
        Integer xRotation,
        Integer yRotation,
        Integer zRotation
) {
    public static DrawingPartsData from(DrawingPart part) {
        return new DrawingPartsData(
                part.getId(),
                part.getXCoordinate(),
                part.getYCoordinate(),
                part.getZCoordinate(),
                part.getXRotation(),
                part.getYRotation(),
                part.getZRotation()
        );
    }
}
