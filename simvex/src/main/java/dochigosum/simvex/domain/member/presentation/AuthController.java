package dochigosum.simvex.domain.member.presentation;

import dochigosum.simvex.domain.member.presentation.dto.JoinRequest;
import dochigosum.simvex.domain.member.presentation.dto.JoinResponse;
import dochigosum.simvex.domain.member.presentation.dto.LoginRequest;
import dochigosum.simvex.domain.member.presentation.dto.LoginResponse;
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
}
