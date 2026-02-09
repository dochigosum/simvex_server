package dochigosum.simvex.domain.member.presentation.dto;

public record VerifyEmailResponse(
        String email,
        boolean emailVerified
) {
}
