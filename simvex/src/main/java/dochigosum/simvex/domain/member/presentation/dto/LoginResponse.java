package dochigosum.simvex.domain.member.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    @JsonProperty("access_token")
    private String accessToken;

    /**
     * Refresh 토큰은 구현하지 않지만, 기존 명세서의 응답 형태를 최대한 맞추기 위해 빈 문자열을 내려줍니다.
     * 필요 없으면 이 필드를 제거해도 됩니다.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
}
