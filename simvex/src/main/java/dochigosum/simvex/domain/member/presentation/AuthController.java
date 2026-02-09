package dochigosum.simvex.domain.member.presentation;

import dochigosum.simvex.domain.member.presentation.dto.*;
import dochigosum.simvex.domain.member.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<JoinResponse> join(@Valid @RequestBody JoinRequest request) {
        return ResponseEntity.ok(authService.join(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/join/verify")
    public ResponseEntity<VerifyEmailResponse> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        return ResponseEntity.ok(authService.verifyEmail(request));
    }
}
