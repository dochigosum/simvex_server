package dochigosum.simvex.domain.member.service;

import dochigosum.simvex.domain.member.entity.Member;
import dochigosum.simvex.domain.member.exception.MemberErrorCode;
import dochigosum.simvex.domain.member.presentation.dto.*;
import dochigosum.simvex.domain.member.repository.MemberRepository;
import dochigosum.simvex.domain.memberverifications.entity.MemberVerification;
import dochigosum.simvex.domain.memberverifications.repository.MemberVerificationRepository;
import dochigosum.simvex.global.error.exception.SimvexException;
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
//        if (memberRepository.existsByEmail(request.email())) {
//            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
//        }

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
            throw new SimvexException(MemberErrorCode.EMAIL_ALREADY_EXISTS);

        }
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new SimvexException(MemberErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new SimvexException(MemberErrorCode.INVALID_CREDENTIALS);
        }

        if (!member.isEmailVerified()) {
            throw new SimvexException(MemberErrorCode.EMAIL_NOT_VERIFIED);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getEmail());
        return new LoginResponse(accessToken, "");
    }

    @Transactional
    public VerifyEmailResponse verifyEmail(VerifyEmailRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new SimvexException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (member.isEmailVerified()) {
            return new VerifyEmailResponse(member.getEmail(), true);
        }

        MemberVerification verification = memberVerificationRepository.findTopByMemberOrderByIdDesc(member)
                .orElseThrow(() -> new SimvexException(MemberErrorCode.VERIFICATION_CODE_NOT_FOUND));

        if (!verification.getCode().equals(request.code())) {
            throw new SimvexException(MemberErrorCode.INVALID_VERIFICATION_CODE);
        }

        if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new SimvexException(MemberErrorCode.VERIFICATION_CODE_EXPIRED);
        }

        member.verifyEmail();
        memberVerificationRepository.deleteAllByMember(member);

        return new VerifyEmailResponse(member.getEmail(), true);
    }
}