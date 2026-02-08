package dochigosum.simvex.domain.drawing.presentation.dto.request;

public record DrawingPartsRequestDTO<T>(
        Long drawingId,
        T drawing
) {
}