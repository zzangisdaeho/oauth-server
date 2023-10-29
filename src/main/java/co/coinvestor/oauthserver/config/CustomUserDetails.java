package co.coinvestor.oauthserver.config;

import co.coinvestor.oauthserver.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 기존 권한 목록
        Set<GrantedAuthority> grantedAuthorities = user.getAuthorizes().stream()
                .map(au -> new SimpleGrantedAuthority(au.getAuthorization().name()))
                .collect(Collectors.toSet());

        // 효과적인 권한 목록 추가
        Set<GrantedAuthority> effectiveAuthorities = user.getEffectiveAuthorizations().stream()
                .map(effectiveAuth -> new SimpleGrantedAuthority(effectiveAuth.name()))
                .collect(Collectors.toSet());

        // 두 권한 목록 합치기
        grantedAuthorities.addAll(effectiveAuthorities);

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.getUserStatus().equals(UserEntity.UserStatus.ACTIVE);
    }

    public Long getUserId() {
        return user.getId();
    }
}
