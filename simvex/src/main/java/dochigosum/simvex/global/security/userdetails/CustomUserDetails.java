package dochigosum.simvex.global.security.userdetails;

import dochigosum.simvex.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final boolean emailVerified;
    private final List<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String email, String password, boolean emailVerified,
                             List<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.emailVerified = emailVerified;
        this.authorities = authorities;
    }

    public static CustomUserDetails from(Member member) {
        return new CustomUserDetails(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.isEmailVerified(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 이메일 인증을 구현하지 않으니 일단 true
        return true;
    }
}
