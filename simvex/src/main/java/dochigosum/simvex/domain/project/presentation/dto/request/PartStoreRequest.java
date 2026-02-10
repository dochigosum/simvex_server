package dochigosum.simvex.domain.project.presentation.dto.request;

public record PartStoreRequest(
        Long id,
        Integer xCoordinate,
        Integer yCoordinate,
        Integer zCoordinate,
        Integer xRotation,
        Integer yRotation,
        Integer zRotation
) {
}