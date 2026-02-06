package dochigosum.simvex.global.config.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class S3ReadService {

    @Value("${BUCKET_NAME}")
    private String bucket;
    @Value("${AWS_REGION}")
    private String region;

    public byte[] getGltfFile(String fileName) {
        String url = "https://" + bucket + ".s3." + region + ".amazonaws.com/3d_asset/" + fileName;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, byte[].class);
    }
}
