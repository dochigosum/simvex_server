package dochigosum.simvex.domain.drawing.presentation.dto.response;

public record DrawingDetailResponseDTO(String name, String detail) {

    public static DrawingDetailResponseDTO of(String name, String detail) {
        return new DrawingDetailResponseDTO(name, detail);
    }
}
