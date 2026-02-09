package dochigosum.simvex.domain.drawing.presentation.dto.response;

public record DrawingExplainResponse(
        String name, String detail
) {
    public static DrawingExplainResponse of(String name, String detail) {
        return new DrawingExplainResponse(name, detail);
    }
}
