package co.coinvestor.oauthserver.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    /**
     * token에서 user_name 컬럼을 없에기 위한 설정
     * @param authentication an authentication representing a user
     * @return
     */
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
//        response.put(USERNAME, authentication.getName());

        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        return response;
    }
}