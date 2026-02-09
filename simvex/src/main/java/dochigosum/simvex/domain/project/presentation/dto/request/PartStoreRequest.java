package dochigosum.simvex.domain.project.presentation.dto.request;

public record PartStoreRequest(
        Long id,
        Long xCoordinate,
        Long yCoordinate,
        Long zCoordinate,
        Long xRotation,
        Long yRotation,
        Long zRotation
) {
}