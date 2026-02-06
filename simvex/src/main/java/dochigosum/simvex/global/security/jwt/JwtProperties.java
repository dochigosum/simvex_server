package dochigosum.simvex.global.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret = "${jwt.secret}";

    private long accessExpirationMs = Long.parseLong("${jwt.access-token-expiration}");
}
