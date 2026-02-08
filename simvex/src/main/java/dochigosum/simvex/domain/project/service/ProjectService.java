package dochigosum.simvex.domain.project.service;

import dochigosum.simvex.domain.project.presentation.dto.request.ProjectCreateRequest;
import dochigosum.simvex.domain.project.presentation.dto.response.ProjectResponse;
import dochigosum.simvex.domain.project.presentation.dto.response.ProjectListResponse;
import dochigosum.simvex.domain.project.entity.Project;
import dochigosum.simvex.domain.project.exception.ProjectCreationException;
import dochigosum.simvex.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        Project project = Project.builder()
                .userId(request.userId())
                .name(request.name())
                .previewImgUrl(request.previewImgUrl())
                .build();

        Project savedProject = projectRepository.save(project);
        return ProjectResponse.from(savedProject);
    }

    public ProjectListResponse getProjectsByUserId(Long userId) {
        List<Project> projects = projectRepository.findByUserId(userId);

        List<ProjectResponse> projectResponses = projects.stream()
                .map(ProjectResponse::from)
                .toList();

        return ProjectListResponse.from(projectResponses);
    }
}