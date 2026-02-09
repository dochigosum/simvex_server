package dochigosum.simvex.domain.drawing.presentation.dto.response;

public record DrawingExplainResponseDTO(String name, String detail) {
    public static DrawingExplainResponseDTO of(String name, String detail) {
        return new DrawingExplainResponseDTO(name, detail);
    }
}
