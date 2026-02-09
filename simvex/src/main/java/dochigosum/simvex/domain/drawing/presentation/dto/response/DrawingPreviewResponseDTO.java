package dochigosum.simvex.domain.drawing.presentation.dto.response;

import dochigosum.simvex.domain.drawing.entity.Drawing;

public record DrawingPreviewResponseDTO(
        Long id,
        String name,
        String previewImgUrl
) {
    public static DrawingPreviewResponseDTO of(Long id, String name, String previewImgUrl) {
        return new DrawingPreviewResponseDTO(id, name, previewImgUrl);
    }
}
