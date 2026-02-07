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

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        try {
            Project project = Project.builder()
                    .userId(request.userId())
                    .name(request.name())
                    .previewImgUrl(request.previewImgUrl())
                    .build();

            Project savedProject = projectRepository.save(project);
            log.info("프로젝트 생성 완료. ID: {}", savedProject.getId());

            return ProjectResponse.from(savedProject);
        } catch (Exception e) {
            log.error("프로젝트 생성 실패", e);
            throw new ProjectCreationException("프로젝트 생성에 실패했습니다.", e);
        }
    }

    @Transactional(readOnly = true)
    public ProjectListResponse getProjectsByUserId(Long userId) {
        List<Project> projects = projectRepository.findByUserId(userId);
        List<ProjectResponse> responses = projects.stream()
                .map(ProjectResponse::from)
                .toList();

        return ProjectListResponse.from(responses);
    }
}