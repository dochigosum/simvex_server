package dochigosum.simvex.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties properties;
    private SecretKey key;

    public static final String CLAIM_TYPE = "type";
    public static final String TOKEN_TYPE_ACCESS = "ACCESS";
    public static final String CLAIM_AUTHORITIES = "authorities";

    @PostConstruct
    void init() {
        // HS256은 최소 256bit(32바이트) 비밀키 권장
        this.key = Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long memberId, String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + properties.getAccessExpirationMs());

        // 지금은 권한 모델이 없으니 기본 ROLE_USER
        List<String> authorities = List.of("ROLE_USER");

        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim(CLAIM_TYPE, TOKEN_TYPE_ACCESS)
                .claim("email", email)
                .claim(CLAIM_AUTHORITIES, authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        Object authClaim = claims.get(CLAIM_AUTHORITIES);
        Collection<? extends GrantedAuthority> authorities;
        if (authClaim instanceof Collection<?> c) {
            authorities = c.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        } else {
            authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // principal을 memberId로 간단히 둠 (필요하면 UserDetails로 변경 가능)
        String principal = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Long getMemberId(String token) {
        return Long.parseLong(parseClaims(token).getSubject());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
