package dochigosum.simvex.global.security.userdetails;

import dochigosum.simvex.domain.member.entity.Member;
import dochigosum.simvex.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return CustomUserDetails.from(member);
    }

    // JWT subject(memberId) 기반 인증을 위해 추가한 메서드
    public CustomUserDetails loadUserById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return CustomUserDetails.from(member);
    }
}
