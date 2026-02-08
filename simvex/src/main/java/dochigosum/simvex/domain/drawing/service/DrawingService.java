package dochigosum.simvex.domain.drawing.service;

import dochigosum.simvex.domain.drawing.entity.Drawing;
import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingData;
import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingExplainResponseDTO;
import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingPreviewResponseDTO;
import dochigosum.simvex.domain.drawing.presentation.dto.response.ViewDrawingsResponseDTO;
import dochigosum.simvex.domain.drawing.repository.DrawingRepository;
import dochigosum.simvex.global.error.GlobalErrorCode;
import dochigosum.simvex.global.error.exception.SimvexException;
import dochigosum.simvex.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrawingService {

    private final DrawingRepository drawingRepository;
    private final S3Service s3Service;

    //조립도 목록 조회
    public ViewDrawingsResponseDTO<List<DrawingData>> getDrawingList() {


        List<DrawingData> drawingDataList = drawingRepository.findAll()
                .stream()
                .map(DrawingData::from)
                .toList();

        return new ViewDrawingsResponseDTO<>(
                "SUCCESS",
                drawingDataList
        );

    }
    //조립도 검색 /search?query={} -> 검색 결과 + 미리보기 이미지 띄움
    public List<DrawingPreviewResponseDTO> searchDrawing(String search) {
        // 검색
        List<Drawing> drawings = drawingRepository.findByNameContainingIgnoreCase(search);
        if (drawings.isEmpty()) {
            throw new SimvexException(GlobalErrorCode.NOT_FOUND, "검색 결과가 없습니다: " + search);

        }
        return drawings.stream()
                .map(d -> DrawingPreviewResponseDTO.from(
                        d.getId(),
                        d.getName(),
                        s3Service.getDrawingPreviewImgUrl(d.getName()) //이걸로 인해서 drawing 등록시 s3파일명과 일치하게 해야함
                ))
                .toList();
    }

    // todo drawingTemplateId 명시후, 이후에 개발
    // 조립도 선택 /{drawingTemplateId}
//    public DrawingPreviewResponseDTO selectDrawingTemplate(Long drawingTemplateId) {
//
//        Drawing drawing = drawingRepository.findById(drawingId)
//                .orElseThrow(() -> new RuntimeException("Drawing not found"));
//
//        String previewUrl = s3Service.getDrawingPreviewImgUrl(drawing.getName());
//
//        return DrawingPreviewResponseDTO.from(drawing.getId(), drawing.getName(), previewUrl);
//    }


    // 조립도 설명
    public DrawingExplainResponseDTO drawingExplain(Long drawingId) {
        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new SimvexException(GlobalErrorCode.NOT_FOUND, "Drawing not found")););
        return DrawingExplainResponseDTO.from(drawing.getDetail());
    }



    //================
    //todo 조립도 전체 저장 /{drawing_id} -> drawing-part 도메인으로 이동
    //todo DrawingPart 개발시 이후에 추가 -> drawing-part 도메인으로 이동
//    @Transactional
//    public void updateDrawingParts(
//            Long drawingId,
//            DrawingPartsRequestDTO<DrawingPartsData> requestDTO) {
//
//        Drawing drawing = drawingRepository.findById(drawingId)
//                .orElseThrow(() -> new RuntimeException("Drawing not found"));
//
//        drawing.getParts().clear();
//
//        List<DrawingPart> updatedParts = requestDTO.drawing().stream()
//                .map(dto -> {
//                    DrawingPart part = new DrawingPart();
//                    part.setDrawing(drawing);
//                    part.setXCoordinate(dto.xCoordinate());
//                    part.setYCoordinate(dto.yCoordinate());
//                    part.setZCoordinate(dto.zCoordinate());
//                    part.setXRotation(dto.xRotation());
//                    part.setYRotation(dto.yRotation());
//                    part.setZRotation(dto.zRotation());
//                    return part;
//                })
//                .toList();
//
//        drawing.getParts().addAll(updatedParts);
//
//        drawingRepository.save(drawing);
//    }





}