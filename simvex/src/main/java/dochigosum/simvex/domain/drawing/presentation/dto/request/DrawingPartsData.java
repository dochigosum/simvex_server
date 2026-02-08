package dochigosum.simvex.domain.drawing.presentation.dto.request;

public record DrawingPartsData(
        Long partId,
        int xCoordinate,
        int yCoordinate,
        int zCoordinate,
        int xRotation,
        int yRotation,
        int zRotation
) {

    //todo 이후에 DrawingPart 추가된 후에 수정
//    public static DrawingPartsData from(DrawingPart part) {
//        return new DrawingPartsData(
//                part.getId(),
//                part.getXCoordinate(),
//                part.getYCoordinate(),
//                part.getZCoordinate(),
//                part.getXRotation(),
//                part.getYRotation(),
//                part.getZRotation()
//        );
//    }
}
