package dochigosum.simvex.domain.drawing.presentation;

import dochigosum.simvex.domain.drawing.presentation.dto.request.DrawingAssetRequest;
import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingAssetResponse;
import dochigosum.simvex.domain.drawing.presentation.dto.response.*;
import dochigosum.simvex.domain.drawing.service.DrawingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/part/asset")
    public ResponseEntity<List<DrawingAssetResponse>> getHomeAssets(
            @RequestBody DrawingAssetRequest request) {
        return ResponseEntity.ok(drawingService.getDrawingAssets(request.drawingTemplateId()));
    }

    @PostMapping("/template/select")
    public ResponseEntity<Void> selectDrawingTemplate(
            @RequestBody DrawingAssetRequest request,
            @AuthenticationPrincipal Long userId) {
        drawingService.createDrawingSession(request.drawingTemplateId(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

