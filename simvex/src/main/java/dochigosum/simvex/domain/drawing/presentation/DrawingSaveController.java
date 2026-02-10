package dochigosum.simvex.domain.drawing.presentation;

import dochigosum.simvex.domain.drawing.presentation.dto.request.PartTransformRequest;
import dochigosum.simvex.domain.drawing.service.DrawingSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/drawing")
class DrawingSaveController {

    private final DrawingSaveService drawingSaveService;

    @PostMapping("/restore")
    public ResponseEntity<Void> saveModeling(@RequestBody List<PartTransformRequest> requests) {
        drawingSaveService.saveToRedis(requests);
        return ResponseEntity.ok().build();
    }
}