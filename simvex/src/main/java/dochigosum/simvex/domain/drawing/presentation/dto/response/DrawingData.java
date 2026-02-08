package dochigosum.simvex.domain.drawing.presentation.dto.response;

import dochigosum.simvex.domain.drawing.entity.Drawing;

public record DrawingData(
        Long id,
        String drawingName,
        String description
) {

    public static DrawingData from(Drawing drawing) {
        return new DrawingData(
                drawing.getId(),
                drawing.getName(),
                drawing.getDetail()
        );
    }
}