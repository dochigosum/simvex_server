package dochigosum.simvex.global.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class S3ReadService {

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

    /// project/{member_id}/{project_name}/저장할파일명
    public String getProjectImgUrl(String memberId, String projectName, String fileName) {
        return "https://" +
                bucket +
                ".s3." +
                region +
                ".amazonaws.com/project/" +
                memberId + "/" +
                projectName + "/" +
                fileName;
    }
}
