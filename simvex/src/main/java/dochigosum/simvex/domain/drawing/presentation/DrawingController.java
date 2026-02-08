package dochigosum.simvex.domain.drawing.presentation;

import dochigosum.simvex.domain.drawing.presentation.dto.request.DrawingPartsData;
import dochigosum.simvex.domain.drawing.presentation.dto.request.DrawingPartsRequestDTO;
import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingData;
import dochigosum.simvex.domain.drawing.presentation.dto.response.ViewDrawingsResponseDTO;
import dochigosum.simvex.domain.drawing.service.DrawingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drawing")
@RequiredArgsConstructor
public class DrawingController {

    private final DrawingService drawingService;

    //조립도 목록 조회
    @GetMapping("/")
    public ResponseEntity<ViewDrawingsResponseDTO<List<DrawingData>>> getDrawingList(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(drawingService.getDrawingList());
    }

    // todo drawingTemplateId 명시후, 이후에 개발
    //조립도 검색 /search?query={} -> 검색결과 + 미리보기 이미지 띄움
    @GetMapping("/search")
    public ResponseEntity<?> searchDrawing(@RequestParam String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(drawingService.searchDrawing(search));
    }


    //조립도 선택
//    @GetMapping("/{drawing_template_id}")
//    public ResponseEntity<?> selectDrawing(@PathVariable("drawing_template_id") String drawingTemplateId){
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(drawingService.selectDrawingTemplate(drawingTemplateId));
//    }



    //조립도 설명 /{drawing_id}/explain
    @GetMapping("/{drawing_id}/explain")
    public ResponseEntity<?> drawingDetail(@PathVariable("drawing_id") Long drawingId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(drawingService.drawingExplain(drawingId));
    }


    //================
    //todo -> drawing-part 도메인으로 이동
    //조립도 전체 저장 /{drawing_id}
//    @PutMapping("/{drawing_id}")
//    public ResponseEntity<?> saveParts(
//            @PathVariable("draawing_id") Long drawingId,
//            @RequestBody DrawingPartsRequestDTO<DrawingPartsData> data) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(drawingService.updateDrawingParts(drawingId, data));
//    }


}
