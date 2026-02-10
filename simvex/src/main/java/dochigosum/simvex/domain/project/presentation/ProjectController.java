package dochigosum.simvex.domain.project.presentation;

import dochigosum.simvex.domain.project.presentation.dto.request.*;
import dochigosum.simvex.domain.project.presentation.dto.response.*;
import dochigosum.simvex.domain.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectCreateRequest request
    ) {
        ProjectResponse response = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDetailResponse> getProjectDetail(
            @PathVariable Long projectId
    ) {
        ProjectDetailResponse response = projectService
                .getProjectDetailById(projectId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{projectId}/detail")
    public ResponseEntity<ProjectDetailResponse> getProjectDetailByName(
            @PathVariable String projectId
    ) {
        ProjectDetailResponse response = projectService
                .getProjectDetailByName(projectId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{projectId}/rename")
    public ResponseEntity<ProjectDetailResponse> renameProject(
            @PathVariable String projectId,
            @Valid @RequestBody ProjectRenameRequest request
    ) {
        ProjectDetailResponse response = projectService
                .renameProject(projectId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{projectId}/delete")
    public ResponseEntity<ProjectDeleteResponse> deleteProject(
            @PathVariable String projectId
    ) {
        ProjectDeleteResponse response = projectService
                .deleteProject(projectId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{projectId}/store")
    public ResponseEntity<ProjectStoreResponse> storeProject(
            @PathVariable String projectId,
            @RequestPart("partInfo") String partInfo,
            @RequestPart(value = "saveImage", required = false) MultipartFile saveImage,
            @RequestParam(defaultValue = "false") boolean persistToDb
    ) {
        ProjectStoreResponse response = projectService.storeProjectParts(
                projectId, partInfo, saveImage, persistToDb
        );
        return ResponseEntity.ok(response);
    }
}