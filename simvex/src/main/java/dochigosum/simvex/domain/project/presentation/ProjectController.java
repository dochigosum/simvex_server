package dochigosum.simvex.domain.project.presentation;

import dochigosum.simvex.domain.project.presentation.dto.request.ProjectCreateRequest;
import dochigosum.simvex.domain.project.presentation.dto.response.ProjectResponse;
import dochigosum.simvex.domain.project.presentation.dto.response.ProjectListResponse;
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

    @GetMapping
    public ResponseEntity<ProjectListResponse> getProjects(
            @RequestParam Long userId
    ) {
        ProjectListResponse response = projectService.getProjectsByUserId(userId);
        return ResponseEntity.ok(response);
    }
}