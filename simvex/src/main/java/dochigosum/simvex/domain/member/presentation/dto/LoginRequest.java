package dochigosum.simvex.domain.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(min = 4, max = 255)
    private String password;
}
