package dochigosum.simvex.domain.project.presentation;

import dochigosum.simvex.domain.project.presentation.dto.request.*;
import dochigosum.simvex.domain.project.presentation.dto.response.*;
import dochigosum.simvex.domain.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
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

    @GetMapping("/{project_name}/detail")
    public ResponseEntity<ProjectDetailResponse> getProjectDetailByName(
            @PathVariable String project_name
    ) {
        ProjectDetailResponse response = projectService
                .getProjectDetailByName(project_name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{project_name}/rename")
    public ResponseEntity<ProjectDetailResponse> renameProject(
            @PathVariable String project_name,
            @Valid @RequestBody ProjectRenameRequest request
    ) {
        ProjectDetailResponse response = projectService
                .renameProject(project_name, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{project_name}/delete")
    public ResponseEntity<ProjectDeleteResponse> deleteProject(
            @PathVariable String project_name
    ) {
        ProjectDeleteResponse response = projectService
                .deleteProject(project_name);
        return ResponseEntity.ok(response);
    }
}