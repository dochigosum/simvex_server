package dochigosum.simvex.domain.project.service;

import dochigosum.simvex.domain.common.CoordinateAttribute;
import dochigosum.simvex.domain.common.RotationAttribute;
import dochigosum.simvex.domain.project.entity.Part;
import dochigosum.simvex.domain.project.presentation.dto.request.*;
import dochigosum.simvex.domain.project.presentation.dto.response.*;
import dochigosum.simvex.domain.project.entity.Project;
import dochigosum.simvex.domain.project.repository.PartRepository;
import dochigosum.simvex.domain.project.repository.ProjectRepository;
import dochigosum.simvex.domain.template.entity.PartTemplate;
import dochigosum.simvex.domain.template.repository.PartTemplateRepository;
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
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PartRepository partRepository;
    private final PartTemplateRepository partTemplateRepository;
    private final ProjectRedisService projectRedisService;
    private final S3Service s3Service;

    @Transactional
    public void createProject(Long memberId, ProjectCreateRequest request) {

        Project project = Project.builder()
                .memberId(memberId)
                .name(request.name())
                .build();
        projectRepository.save(project);
    }

    public List<ProjectsResponse> getProjects(Long memberId) {
        List<Project> projects = projectRepository.findAllByMemberId((memberId));

        return projects.stream()
                .map(ProjectsResponse::from)
                .toList();
    }

    public ProjectDetailResponse getProjectDetail(Long projectId) {
        Project project = findProjectById(projectId);
        return ProjectDetailResponse.from(project);
    }

    @Transactional
    public ProjectDetailResponse renameProject(
            Long projectId,
            ProjectRenameRequest request
    ) {
        Project project = findProjectById(projectId);

        project.rename(request.newName());
        return ProjectDetailResponse.from(project);
    }

    @Transactional
    public ProjectDeleteResponse deleteProject(Long projectId) {
        Project project = findProjectById(projectId);
        projectRepository.delete(project);
        return ProjectDeleteResponse.of(project.getName());
    }

    private Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new SimvexException(
                        GlobalErrorCode.PROJECT_NOT_FOUND,
                        "ID: " + projectId
                ));
    }

    // 프로젝트에 부품 추가(부품 로딩)
    @Transactional
    public ModelFetchResponse fetchModelInfo(Long projectId, PartAddRequest request) {
        Project project = findProjectById(projectId);
        PartTemplate partTemplate = partTemplateRepository.findById(request.partTemplateId())
                .orElseThrow(() -> new SimvexException(GlobalErrorCode.PART_NOT_FOUND, "partTemplateId=" + request.partTemplateId()));

        String modelUrl = s3Service.getPartModelUrl(partTemplate.getDrawingTemplate().getName(), partTemplate.getModelFileName());

        Part newPart = Part.builder()
                .name(partTemplate.getName())
                .modelFileName(partTemplate.getModelFileName())
                .project(project)
                .coordinate(partTemplate.getCoordinateAttribute())
                .rotation(partTemplate.getRotationAttribute())
                .build();

        partRepository.save(newPart);

        return new ModelFetchResponse(partTemplate.getModelFileName(), modelUrl);
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