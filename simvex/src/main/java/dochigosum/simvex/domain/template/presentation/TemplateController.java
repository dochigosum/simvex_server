package dochigosum.simvex.domain.template.presentation;

import dochigosum.simvex.domain.template.presentation.dto.response.DrawingListResponse;
import dochigosum.simvex.domain.template.presentation.dto.response.DrawingSearchResponse;
import dochigosum.simvex.domain.template.presentation.dto.response.PartListResponse;
import dochigosum.simvex.domain.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/template")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping("/drawing")
    public ResponseEntity<List<DrawingListResponse>> getDrawings() {
        return ResponseEntity.ok(templateService.getDrawings());
    }

    @GetMapping("/drawing/search")
    public ResponseEntity<List<DrawingSearchResponse>> searchDrawings(
            @RequestParam String query) {
        return ResponseEntity.ok(templateService.searchDrawings(query));
    }

    @GetMapping("/{drawing_template_id}/part")
    public ResponseEntity<List<PartListResponse>> getParts(
            @PathVariable("drawing_template_id") Long id) {
        return ResponseEntity.ok(templateService.getParts(id));
    }
}
