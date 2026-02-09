package dochigosum.simvex.domain.drawing.presentation.dto.request;

public record SaveDrawingPartsRequestDTO(
        Long partId,
        Integer xCoordinate,
        Integer yCoordinate,
        Integer zCoordinate,
        Integer xRotation,
        Integer yRotation,
        Integer zRotation
) {
    public static SaveDrawingPartsRequestDTO from(Long partId,
                                                  Integer xCoordinate,
                                                  Integer yCoordinate,
                                                  Integer zCoordinate,
                                                  Integer xRotation,
                                                  Integer yRotation,
                                                  Integer zRotation) {
        return new SaveDrawingPartsRequestDTO(
                partId,
                xCoordinate,
                yCoordinate,
                zCoordinate,
                xRotation,
                yRotation,
                zRotation);
    }
}
