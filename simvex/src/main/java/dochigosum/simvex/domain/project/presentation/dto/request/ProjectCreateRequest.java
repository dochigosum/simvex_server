package dochigosum.simvex.domain.project.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectCreateRequest(
        @NotNull(message = "사용자 ID는 필수입니다")
        Long userId,

        @NotBlank(message = "프로젝트 이름은 필수입니다")
        String name,

        String previewImgUrl
) {
}
