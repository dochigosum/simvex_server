package dochigosum.simvex.global.s3;

import dochigosum.simvex.global.error.GlobalErrorCode;
import dochigosum.simvex.global.error.exception.SimvexException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class S3Service {

    private final S3Client s3Client;
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Value("${BUCKET_NAME}")
    private String bucket;

    @Value("${AWS_REGION}")
    private String region;

    public String getDrawingUrl(String drawing, String fileName) {
        return "https://" +
                bucket +
                ".s3." +
                region +
                ".amazonaws.com/3d_asset/" +
                drawing + "/" +
                fileName;
    }

    // project/{member_id}/{project_name}/저장할파일명 => 유저의 프로젝트 파일명
    public String getProjectImgUrl(Long memberId, String projectName, String fileName) {
        return "https://" +
                bucket +
                ".s3." +
                region +
                ".amazonaws.com/project/" +
                memberId + "/" +
                projectName + "/" +
                fileName;
    }

    // preview/파일명
    // getDrawingPreviewImgUrl
    public String getDrawingPreviewImgUrl(String drawingName) {
        return "https://" +
                bucket +
                ".s3." +
                region +
                ".amazonaws.com/drawing/" +
                drawingName;
    }

    // uploadProjectImg
    public String uploadProjectPreviewImg(Long memberId, String projectName, MultipartFile file) {

        validateContentType(file.getContentType());

        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFileName;
        String key = "project/" + memberId + "/" + projectName + "/" + fileName;

        // 기존 파일 삭제
        deleteObject(key);

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            log.info("S3 업로드 완료: bucket={}, key={}", bucket, key);
            return getProjectImgUrl(memberId, projectName, fileName);

        } catch (IOException e) {
            throw new SimvexException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "파일 업로드 중 오류 발생");
        }
    }

    private void validateContentType(String contentType) {
        if (contentType == null || !contentType.startsWith("image/")) {
            log.warn("허용되지 않는 파일 형식: {}", contentType);
            throw new SimvexException(GlobalErrorCode.INVALID_REQUEST, "이미지 파일(jpg, png, gif 등)만 업로드할 수 있습니다.");
        }
    }

    private void deleteObject(String key) {
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteRequest);
        } catch (Exception e) {
            log.debug("S3 삭제 실패 : key={}, error={}", key, e.getMessage());
        }
    }
}