package dochigosum.simvex.domain.drawing.presentation;

import dochigosum.simvex.domain.drawing.presentation.dto.response.*;
import dochigosum.simvex.domain.drawing.service.DrawingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drawing")
@RequiredArgsConstructor
public class DrawingController {

    private final DrawingService drawingService;

    //조립도 정보 반환 /{drawing_id}/explain
    @GetMapping("/{drawingId}/explain")
    public ResponseEntity<DrawingExplainResponse> getDrawingExplain(@PathVariable Long drawingId) {
        return ResponseEntity.ok(drawingService.drawingExplain(drawingId));
    }

    @GetMapping("/{drawingId}/part")
    public ResponseEntity<List<DrawingPartsResponse>> getDrawingParts(@PathVariable Long drawingId) {
        return ResponseEntity.ok(drawingService.getParts(drawingId));
    }

    @GetMapping("/{drawingId}/model")
    public ResponseEntity<List<PartsModelResponse>> getDrawingModels(@PathVariable Long drawingId) {
        return ResponseEntity.ok(drawingService.getModel(drawingId));
    }

    @GetMapping("/part/{partId}/explain")
    public ResponseEntity<PartExplainResponse> getPartExplain(@PathVariable Long partId) {
        return ResponseEntity.ok(drawingService.getPart(partId));
    }

    @GetMapping("/part/{partId}/model")
    public ResponseEntity<PartModelUrlResponse> getPartModel(@PathVariable Long partId) {
        return ResponseEntity.ok(drawingService.getModelUrl(partId));
    }
}

