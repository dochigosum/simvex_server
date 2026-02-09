package dochigosum.simvex.domain.drawing.presentation.dto.request;

public record SaveDrawingPartsRequest(
        Long partId,
        Integer xCoordinate,
        Integer yCoordinate,
        Integer zCoordinate,
        Integer xRotation,
        Integer yRotation,
        Integer zRotation
) {
    public static SaveDrawingPartsRequest from(
            Long partId,
            Integer xCoordinate,
            Integer yCoordinate,
            Integer zCoordinate,
            Integer xRotation,
            Integer yRotation,
            Integer zRotation) {
        return new SaveDrawingPartsRequest(
                partId,
                xCoordinate,
                yCoordinate,
                zCoordinate,
                xRotation,
                yRotation,
                zRotation);
    }
}
