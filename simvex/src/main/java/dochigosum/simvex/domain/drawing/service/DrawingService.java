package dochigosum.simvex.domain.drawing.service;

import dochigosum.simvex.domain.drawing.entity.Drawing;
import dochigosum.simvex.domain.drawing.entity.DrawingPart;
import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingPartsResponseData;
import dochigosum.simvex.domain.drawing.presentation.dto.request.DrawingPartsRequestDTO;
import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingData;
import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingExplainResponseDTO;
import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingPreviewResponseDTO;
import dochigosum.simvex.domain.drawing.presentation.dto.response.ViewDrawingsResponseDTO;
import dochigosum.simvex.domain.drawing.repository.DrawingPartRepository;
import dochigosum.simvex.domain.drawing.repository.DrawingRepository;
import dochigosum.simvex.global.error.GlobalErrorCode;
import dochigosum.simvex.global.error.exception.SimvexException;
import dochigosum.simvex.global.s3.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrawingService {

    private final DrawingRepository drawingRepository;
    private final DrawingPartRepository drawingPartRepository;
    private final S3Service s3Service;


    // 조립도 설명
    public DrawingExplainResponseDTO drawingExplain(Long drawingId) {
        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new SimvexException(GlobalErrorCode.NOT_FOUND, "Drawing not found"));
        return DrawingExplainResponseDTO.of(drawing.getName(), drawing.getDetail());
    }

    // 조립도 전체저장
    @Transactional
    public void saveDrawingParts(Long drawingId, DrawingPartsRequestDTO request) {

        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new SimvexException(GlobalErrorCode.NOT_FOUND, "Drawing not found"));

        // partIds 추출
        List<Long> partIds = request.parts().stream()
                .map(DrawingPartsResponseData::partId)
                .toList();

        List<DrawingPart> parts = drawingPartRepository.findAllByIdIn(partIds);

        if (parts.size() != partIds.size()) {
            throw new SimvexException(GlobalErrorCode.INVALID_REQUEST, "Invalid partId included");
        }

        Map<Long, DrawingPart> partMap = parts.stream()
                .collect(Collectors.toMap(DrawingPart::getId, p -> p));

        for (DrawingPartsResponseData data : request.parts()) {
            DrawingPart part = partMap.get(data.partId());

            if (!part.getDrawing().getId().equals(drawingId)) {
                throw new SimvexException(GlobalErrorCode.FORBIDDEN, "Part does not belong to drawing");
            }

            updatePartState(part, data);
        }
    }

    private void updatePartState(DrawingPart part, DrawingPartsResponseData data) {
        part.updateTransform(
                data.xCoordinate(),
                data.yCoordinate(),
                data.zCoordinate(),
                data.xRotation(),
                data.yRotation(),
                data.zRotation()
        );
    }

    //조립도 목록 조회
    public ViewDrawingsResponseDTO<List<DrawingData>> getDrawingList() {


        List<DrawingData> drawingDataList = drawingRepository.findAll()
                .stream()
                .map(DrawingData::of)
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
                .map(d -> DrawingPreviewResponseDTO.of(
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

}