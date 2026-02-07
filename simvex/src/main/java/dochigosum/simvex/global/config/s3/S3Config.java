package dochigosum.simvex.global.config.s3;

import io.awspring.cloud.autoconfigure.s3.S3ClientCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;

@Configuration
public class S3Config {

    @Bean
    public S3ClientCustomizer s3ClientCustomizer() {
        return builder -> builder
                .credentialsProvider(AnonymousCredentialsProvider.create());
    }
}
