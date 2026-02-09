package dochigosum.simvex.domain.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dochigosum.simvex.domain.project.entity.Part;
import dochigosum.simvex.domain.project.presentation.dto.request.*;
import dochigosum.simvex.domain.project.presentation.dto.response.*;
import dochigosum.simvex.domain.project.entity.Project;
import dochigosum.simvex.domain.project.repository.PartRepository;
import dochigosum.simvex.domain.project.repository.ProjectRepository;
import dochigosum.simvex.global.error.GlobalErrorCode;
import dochigosum.simvex.global.error.exception.SimvexException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PartRepository partRepository;
    private final ProjectRedisService projectRedisService;
    private final ObjectMapper objectMapper;
    private final ProjectS3Service projectS3Service;

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

    public ProjectDetailResponse getProjectDetailByName(String projectId) {
        Project project = findProjectByName(projectId);
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
            String projectName,
            String partInfoJson,
            MultipartFile saveImage,
            boolean persistToDb
    ) {
        Project project = findProjectByName(projectName);
        List<PartStoreRequest> partRequests = parsePartInfo(partInfoJson);

        // Redis에 항상 저장
        projectRedisService.savePartsToRedis(project.getId(), partRequests);

        // MySQL 저장 및 이미지 업로드
        if (persistToDb && saveImage != null && !saveImage.isEmpty()) {
            savePersistentData(project, partRequests, saveImage);
        }

        return ProjectStoreResponse.of(projectName);
    }

    private List<PartStoreRequest> parsePartInfo(String partInfoJson) {
        try {
            return objectMapper.readValue(partInfoJson,
                    objectMapper.getTypeFactory()
                            .constructCollectionType(List.class,
                                    PartStoreRequest.class));
        } catch (JsonProcessingException e) {
            throw new SimvexException(GlobalErrorCode.INVALID_PART_DATA);
        }
    }

    private void savePersistentData(
            Project project,
            List<PartStoreRequest> partRequests,
            MultipartFile saveImage
    ) {
        // 기존 부품 삭제
        partRepository.deleteByProjectId(project.getId());

        // 새 부품 저장
        List<Part> parts = partRequests.stream()
                .map(req -> Part.builder()
                        .projectId(project.getId())
                        .xCoordinate(req.xCoordinate())
                        .yCoordinate(req.yCoordinate())
                        .zCoordinate(req.zCoordinate())
                        .xRotation(req.xRotation())
                        .yRotation(req.yRotation())
                        .zRotation(req.zRotation())
                        .build())
                .toList();

        partRepository.saveAll(parts);

        // S3에 이미지 업로드 및 업데이트
        String imageUrl = projectS3Service.uploadImage(saveImage, project.getName());
        project.updatePreviewImage(imageUrl);
    }
}