package dochigosum.simvex.domain.project.presentation.dto.request;

public record PartStoreRequest(
        Long id,
        Double xCoordinate,
        Double yCoordinate,
        Double zCoordinate,
        Double xRotation,
        Double yRotation,
        Double zRotation
) {
}