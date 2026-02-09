package dochigosum.simvex.domain.drawing.presentation.dto.response;

public record PartModelUrlResponse(
        String partModelUrl
) {
    public static PartModelUrlResponse of(String partModelUrl) {
        return new PartModelUrlResponse(partModelUrl);
    }
}
