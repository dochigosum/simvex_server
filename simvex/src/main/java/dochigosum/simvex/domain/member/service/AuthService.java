package dochigosum.simvex.domain.member.service;

import dochigosum.simvex.domain.member.entity.Member;
import dochigosum.simvex.domain.member.presentation.dto.*;
import dochigosum.simvex.domain.member.repository.MemberRepository;
import dochigosum.simvex.domain.memberverifications.entity.MemberVerification;
import dochigosum.simvex.domain.memberverifications.repository.MemberVerificationRepository;
import dochigosum.simvex.global.mail.service.MailService;
import dochigosum.simvex.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberVerificationRepository memberVerificationRepository;
    private final MailService mailService;

    @Value("${spring.mail.properties.auth-code-expiration-millis:600000}")
    private long authCodeExpirationMillis;

    @Transactional
    public JoinResponse join(JoinRequest request) {
        Member member = Member.of(
                request.email(),
                passwordEncoder.encode(request.password())
        );

        try {
            Member saved = memberRepository.save(member);

            // 이메일 인증 코드 생성 + 저장
            String code = mailService.generateCode();
            LocalDateTime expiresAt = LocalDateTime.now().plusNanos(authCodeExpirationMillis * 1_000_000L);
            memberVerificationRepository.save(MemberVerification.of(saved, code, expiresAt));

            // 이메일 전송
            mailService.sendVerificationEmail(saved.getEmail(), code);

            return new JoinResponse(saved.getEmail());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.", e);
        }
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!member.isEmailVerified()) {
            throw new IllegalArgumentException("이메일 인증이 필요합니다.");
        }

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getEmail());
        return new LoginResponse(accessToken, "");
    }

    @Transactional
    public VerifyEmailResponse verifyEmail(VerifyEmailRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (member.isEmailVerified()) {
            return new VerifyEmailResponse(member.getEmail(), true);
        }

        MemberVerification verification = memberVerificationRepository.findTopByMemberOrderByIdDesc(member)
                .orElseThrow(() -> new IllegalArgumentException("인증 코드가 존재하지 않습니다. 다시 요청해 주세요."));

        if (!verification.getCode().equals(request.code())) {
            throw new IllegalArgumentException("인증 코드가 올바르지 않습니다.");
        }

        if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증 코드가 만료되었습니다. 다시 요청해 주세요.");
        }

        member.verifyEmail();
        memberVerificationRepository.deleteAllByMember(member);

        return new VerifyEmailResponse(member.getEmail(), true);
    }
}