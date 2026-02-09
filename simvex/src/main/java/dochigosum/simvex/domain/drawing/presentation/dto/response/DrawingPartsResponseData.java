package dochigosum.simvex.domain.drawing.presentation.dto.response;

import dochigosum.simvex.domain.drawing.entity.DrawingPart;

public record DrawingPartsResponseData(
        Long partId,
        Integer xCoordinate,
        Integer yCoordinate,
        Integer zCoordinate,
        Integer xRotation,
        Integer yRotation,
        Integer zRotation
) {
    public static DrawingPartsResponseData of(DrawingPart part) {
        return new DrawingPartsResponseData(
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
