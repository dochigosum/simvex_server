package dochigosum.simvex.domain.drawing.presentation.dto.request;

import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingPartsResponseData;

import java.util.List;

public record DrawingPartsRequestDTO(
        List<DrawingPartsResponseData> parts
) {
}