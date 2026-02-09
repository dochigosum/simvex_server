package dochigosum.simvex.domain.project.service;

import dochigosum.simvex.domain.project.presentation.dto.request.*;
import dochigosum.simvex.domain.project.presentation.dto.response.*;
import dochigosum.simvex.domain.project.entity.Project;
import dochigosum.simvex.domain.project.repository.ProjectRepository;
import dochigosum.simvex.global.error.GlobalErrorCode;
import dochigosum.simvex.global.error.exception.SimvexException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        validateProjectNameNotDuplicate(request.name());

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

    public ProjectDetailResponse getProjectDetailById(Long projectId) {
        Project project = findProjectById(projectId);
        return ProjectDetailResponse.from(project);
    }

    public ProjectDetailResponse getProjectDetailByName(String project_name) {
        Project project = findProjectByName(project_name);
        return ProjectDetailResponse.from(project);
    }

    @Transactional
    public ProjectDetailResponse renameProject(
            String currentName,
            ProjectRenameRequest request
    ) {
        Project project = findProjectByName(currentName);
        validateProjectNameNotDuplicate(request.newName());

        project.rename(request.newName());
        return ProjectDetailResponse.from(project);
    }

    @Transactional
    public ProjectDeleteResponse deleteProject(String project_name) {
        Project project = findProjectByName(project_name);
        projectRepository.delete(project);
        return ProjectDeleteResponse.of(project_name);
    }

    private Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new SimvexException(
                        GlobalErrorCode.PROJECT_NOT_FOUND,
                        "ID: " + projectId
                ));
    }

    private Project findProjectByName(String project_name) {
        return projectRepository.findByName(project_name)
                .orElseThrow(() -> new SimvexException(
                        GlobalErrorCode.PROJECT_NOT_FOUND,
                        project_name
                ));
    }

    private void validateProjectNameNotDuplicate(String project_name) {
        if (projectRepository.existsByName(project_name)) {
            throw new SimvexException(
                    GlobalErrorCode.PROJECT_NAME_DUPLICATE,
                    project_name
            );
        }
    }
}