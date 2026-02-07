package dochigosum.simvex.domain.member.service;

import dochigosum.simvex.domain.member.entity.Member;
import dochigosum.simvex.domain.member.presentation.dto.JoinRequest;
import dochigosum.simvex.domain.member.presentation.dto.JoinResponse;
import dochigosum.simvex.domain.member.presentation.dto.LoginRequest;
import dochigosum.simvex.domain.member.presentation.dto.LoginResponse;
import dochigosum.simvex.domain.member.repository.MemberRepository;
import dochigosum.simvex.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public JoinResponse join(JoinRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = Member.of(
                request.email(),
                passwordEncoder.encode(request.password())
        );

        Member saved = memberRepository.save(member);
        return new JoinResponse(saved.getEmail());
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getEmail());
        return new LoginResponse(accessToken, "");
    }
}