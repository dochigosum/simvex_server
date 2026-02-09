package dochigosum.simvex.domain.drawing.presentation.dto.response;

import dochigosum.simvex.domain.drawing.entity.DrawingPart;

public record PartsModelResponse(
        Long partId,
        String partName,
        String partModelUrl,
        Integer xCoordinate,
        Integer yCoordinate,
        Integer zCoordinate,
        Integer xRotation,
        Integer yRotation,
        Integer zRotation
) {
    public static PartsModelResponse from(DrawingPart part, String partModelUrl) {
        return new PartsModelResponse(
                part.getId(),
                part.getName(),
                partModelUrl,
                part.getXCoordinate(),
                part.getYCoordinate(),
                part.getZCoordinate(),
                part.getXRotation(),
                part.getYRotation(),
                part.getZRotation()
        );
    }
}
