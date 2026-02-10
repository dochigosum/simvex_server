package dochigosum.simvex.domain.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dochigosum.simvex.domain.common.CoordinateAttribute;
import dochigosum.simvex.domain.common.RotationAttribute;
import dochigosum.simvex.domain.project.entity.Part;
import dochigosum.simvex.domain.project.presentation.dto.request.*;
import dochigosum.simvex.domain.project.presentation.dto.response.*;
import dochigosum.simvex.domain.project.entity.Project;
import dochigosum.simvex.domain.project.repository.PartRepository;
import dochigosum.simvex.domain.project.repository.ProjectRepository;
import dochigosum.simvex.global.error.GlobalErrorCode;
import dochigosum.simvex.global.error.exception.SimvexException;
import dochigosum.simvex.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PartRepository partRepository;
    private final ProjectRedisService projectRedisService;
    private final ObjectMapper objectMapper;
    private final S3Service s3Service;

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        validateProjectNameNotDuplicate(request.name());

        Project project = Project.builder()
                .memberId(request.memberId())
                .name(request.name())
                .previewImgUrl(request.previewImgUrl())
                .build();

        Project savedProject = projectRepository.save(project);
        return ProjectResponse.from(savedProject);
    }

    public ProjectListResponse getProjects(Long memberId) {
        List<Project> projects = projectRepository.findByMemberId(memberId);

        List<ProjectResponse> projectResponses = projects.stream()
                .map(ProjectResponse::from)
                .toList();

        return ProjectListResponse.from(projectResponses);
    }

    public ProjectDetailResponse getProjectDetailById(Long projectId) {
        Project project = findProjectById(projectId);
        return ProjectDetailResponse.from(project);
    }

    public ProjectDetailResponse getProjectDetailByName(String projectId) {
        Project project = findProjectByName(projectId);
        return ProjectDetailResponse.from(project);
    }

    @Transactional(readOnly = true)
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
    public ProjectDeleteResponse deleteProject(String projectId) {
        Project project = findProjectByName(projectId);
        projectRepository.delete(project);
        return ProjectDeleteResponse.of(projectId);
    }

    private Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new SimvexException(
                        GlobalErrorCode.PROJECT_NOT_FOUND,
                        "ID: " + projectId
                ));
    }

    private Project findProjectByName(String projectId) {
        return projectRepository.findByName(projectId)
                .orElseThrow(() -> new SimvexException(
                        GlobalErrorCode.PROJECT_NOT_FOUND,
                        projectId
                ));
    }

    private void validateProjectNameNotDuplicate(String projectId) {
        if (projectRepository.existsByName(projectId)) {
            throw new SimvexException(
                    GlobalErrorCode.PROJECT_NAME_DUPLICATE,
                    projectId
            );
        }
    }

    @Transactional
    public ProjectStoreResponse storeProjectParts(
            Long projectId,
            List<PartStoreRequest> requests,
            MultipartFile saveImage
    ) {
        Project project = findProjectById(projectId);

        if (saveImage != null && !saveImage.isEmpty()) {
            // MySQL 저장 + S3 이미지 업로드
            return saveToMySqlWithImage(projectId, requests, saveImage);
        }

        // Redis에 항상 저장
        projectRedisService.savePartsToRedis(project.getId(), requests);
        return ProjectStoreResponse.of(project.getName());
    }

    private ProjectStoreResponse saveToMySqlWithImage(Long projectId, List<PartStoreRequest> partInfo, MultipartFile saveImage) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new SimvexException(GlobalErrorCode.PROJECT_NOT_FOUND, "Project ID: " + projectId));

        // 1. S3 이미지 업로드 및 URL 갱신
        String imageUrl = s3Service.uploadProjectPreviewImg(project.getMemberId(), project.getName(), saveImage);
        project.updatePreviewImage(imageUrl);

        // 2. 부품 위치 업데이트 (기존에 만든 updateTransform 활용)
        project.getParts().forEach(part -> {
            partInfo.stream()
                    .filter(req -> req.id().equals(part.getId()))
                    .findFirst()
                    .ifPresent(req -> part.updateTransform(
                            CoordinateAttribute.of(req.xCoordinate(), req.yCoordinate(), req.zCoordinate()),
                            RotationAttribute.of(req.xRotation(), req.yRotation(), req.zRotation())
                    ));
        });

        return new ProjectStoreResponse("Project saved successfully to MySQL", LocalDateTime.now());
    }
}