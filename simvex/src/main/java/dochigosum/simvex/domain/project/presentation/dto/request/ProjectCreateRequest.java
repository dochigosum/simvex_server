package dochigosum.simvex.domain.project.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProjectCreateRequest(
        @NotNull(message = "사용자 ID는 필수입니다")
        @Positive(message = "사용자 ID는 양수여야 합니다")
        Long userId,

        @NotBlank(message = "프로젝트 이름은 필수입니다")
        @Size(max = 255, message = "프로젝트 이름은 255자 이하여야 합니다")
        String name,

        String previewImgUrl
) {
}
