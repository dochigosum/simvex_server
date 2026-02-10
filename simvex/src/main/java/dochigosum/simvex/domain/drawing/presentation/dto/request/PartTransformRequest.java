package dochigosum.simvex.domain.drawing.presentation.dto.request;

public record PartTransformRequest(
        Long partId,
        Long drawingId,
        Double xCoordinate,
        Double yCoordinate,
        Double zCoordinate,
        Double xRotation,
        Double yRotation,
        Double zRotation
) {}