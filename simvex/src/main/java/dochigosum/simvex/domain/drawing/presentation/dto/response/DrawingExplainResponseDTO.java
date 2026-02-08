package dochigosum.simvex.domain.drawing.presentation.dto.response;

public record DrawingExplainResponseDTO(String message) {
    public static DrawingExplainResponseDTO from(String message) {
        return new DrawingExplainResponseDTO(message);
    }
}
