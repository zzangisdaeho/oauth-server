package co.coinvestor.oauthserver.config;

import co.coinvestor.oauthserver.entity.UserEntity;
import co.coinvestor.oauthserver.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity findUser = userEntityRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("not exist user"));

        return new CustomUserDetails(findUser);
    }

    //todo : create update delete 등 기능 추가
}
