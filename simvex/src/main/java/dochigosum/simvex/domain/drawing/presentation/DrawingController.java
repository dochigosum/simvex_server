package dochigosum.simvex.domain.drawing.presentation;

import dochigosum.simvex.domain.drawing.presentation.dto.request.DrawingPartsRequestDTO;
import dochigosum.simvex.domain.drawing.service.DrawingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drawing")
@RequiredArgsConstructor
public class DrawingController {

    private final DrawingService drawingService;

    //조립도 정보 반환 /{drawing_id}/explain
    @GetMapping("/{drawing_id}/explain")
    public ResponseEntity<?> drawingDetail(@PathVariable("drawing_id") Long drawingId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(drawingService.drawingExplain(drawingId));
    }

    //조립도 전체 저장
    @PutMapping("/{drawing_id}")
    public ResponseEntity<?> saveParts(
            @PathVariable("drawing_id") Long drawingId,
            @RequestBody DrawingPartsRequestDTO datas) {
        drawingService.saveDrawingParts(drawingId, datas);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

// todo 도메인별 파트 분배 ====================
    //todo 다른 도메인으로 수정
//    //조립도 목록 조회
//    @GetMapping
//    public ResponseEntity<ViewDrawingsResponseDTO<List<DrawingData>>> getDrawingList(){
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(drawingService.getDrawingList());
//    }
//
//    // todo drawingTemplateId 명시후, 이후에 개발
//    //조립도 검색 /search?query={} -> 검색결과 + 미리보기 이미지 띄움
//    @GetMapping("/search")
//    public ResponseEntity<?> searchDrawing(@RequestParam(name = "query") String search) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(drawingService.searchDrawing(search));
//    }


    //조립도 선택
//    @GetMapping("/{drawing_template_id}")
//    public ResponseEntity<?> selectDrawing(@PathVariable("drawing_template_id") String drawingTemplateId){
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(drawingService.selectDrawingTemplate(drawingTemplateId));
//    }
    //================
    //todo -> drawing-part 도메인으로 이동
    //조립도 전체 저장 /{drawing_id}
//    @PutMapping("/{drawing_id}")
//    public ResponseEntity<?> saveParts(
//            @PathVariable("drawing_id") Long drawingId,
//            @RequestBody DrawingPartsRequestDTO<DrawingPartsData> data) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(drawingService.updateDrawingParts(drawingId, data));
//    }


}
