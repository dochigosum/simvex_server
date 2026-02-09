package dochigosum.simvex.domain.drawing.presentation.dto.request;

public record PartTransformRequest(
        Long partId,
        Long drawingId,
        Integer xCoordinate,
        Integer yCoordinate,
        Integer zCoordinate,
        Integer xRotation,
        Integer yRotation,
        Integer zRotation
) {}