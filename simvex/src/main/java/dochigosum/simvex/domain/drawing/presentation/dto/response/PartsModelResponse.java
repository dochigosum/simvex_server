package dochigosum.simvex.domain.drawing.presentation.dto.response;

import dochigosum.simvex.domain.common.CoordinateAttribute;
import dochigosum.simvex.domain.common.RotationAttribute;
import dochigosum.simvex.domain.drawing.entity.DrawingPart;

public record PartsModelResponse(
        Long partId,
        String partName,
        String partModelUrl,
        Integer xCoordinate,
        Integer yCoordinate,
        Integer zCoordinate,
        Integer xRotation,
        Integer yRotation,
        Integer zRotation
) {
    public static PartsModelResponse from(DrawingPart part, String partModelUrl) {
        // 엔티티에서 임베디드 객체를 먼저 꺼냅니다.
        CoordinateAttribute coordinate = part.getCoordinate();
        RotationAttribute rotation = part.getRotation();

        return new PartsModelResponse(
                part.getId(),
                part.getName(),
                partModelUrl,
                // 임베디드 객체 내부의 필드에 접근 (getter 사용)
                coordinate.getXCoordinate(),
                coordinate.getYCoordinate(),
                coordinate.getZCoordinate(),
                rotation.getXRotation(),
                rotation.getYRotation(),
                rotation.getZRotation()
        );
    }
}
