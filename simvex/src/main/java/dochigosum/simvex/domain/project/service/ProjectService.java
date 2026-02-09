package dochigosum.simvex.domain.project.service;

import dochigosum.simvex.domain.project.exception.ProjectNameDuplicateException;
import dochigosum.simvex.domain.project.exception.ProjectNotFoundException;
import dochigosum.simvex.domain.project.presentation.dto.request.*;
import dochigosum.simvex.domain.project.presentation.dto.response.*;
import dochigosum.simvex.domain.project.entity.Project;
import dochigosum.simvex.domain.project.repository.ProjectRepository;
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
        Project project = Project.builder()
                .userId(request.userId())
                .name(request.name())
                .previewImgUrl(request.previewImgUrl())
                .build();

        Project savedProject = projectRepository.save(project);
        return ProjectResponse.from(savedProject);
    }

    @Transactional(readOnly = true)
    public ProjectListResponse getProjectsByUserId(Long userId) {
        List<Project> projects = projectRepository.findByUserId(userId);
        List<ProjectResponse> responses = projects.stream()
                .map(ProjectResponse::from)
                .toList();

        return ProjectListResponse.from(responses);
    }

    public ProjectDetailResponse getProjectDetailById(Long projectId) {
        Project project = findProjectById(projectId);
        return ProjectDetailResponse.from(project);
    }

    public ProjectDetailResponse getProjectDetailByName(String projectName) {
        Project project = findProjectByName(projectName);
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
    public ProjectDeleteResponse deleteProject(String projectName) {
        Project project = findProjectByName(projectName);
        projectRepository.delete(project);
        return ProjectDeleteResponse.of(projectName);
    }

    private Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        "ID: " + projectId));
    }

    private Project findProjectByName(String projectName) {
        return projectRepository.findByName(projectName)
                .orElseThrow(() -> new ProjectNotFoundException(projectName));
    }

    private void validateProjectNameNotDuplicate(String projectName) {
        if (projectRepository.existsByName(projectName)) {
            throw new ProjectNameDuplicateException(projectName);
        }
    }
}