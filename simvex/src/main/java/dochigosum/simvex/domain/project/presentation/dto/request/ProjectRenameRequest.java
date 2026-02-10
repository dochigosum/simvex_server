package dochigosum.simvex.domain.project.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProjectRenameRequest(
        @NotBlank(message = "새 프로젝트 이름은 필수입니다.")
        String newName
) {
}
