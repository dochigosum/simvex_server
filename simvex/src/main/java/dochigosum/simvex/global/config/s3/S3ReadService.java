package dochigosum.simvex.global.config.s3;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class S3ReadService {

    private final String bucket = "dochigosum-s3";
    private final String region = "ap-northeast-2";

    public byte[] getGltfFile(String fileName) {
        String url = "https://" + bucket + ".s3." + region + ".amazonaws.com/3d_asset/" + fileName;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, byte[].class);
    }
}
