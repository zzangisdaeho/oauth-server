package co.coinvestor.oauthserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomTokenEnhancer implements TokenEnhancer {

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        CustomUserDetails customUserDetails = getCustomUserDetails(authentication.getPrincipal());
        Map<String, Object> additionalInfo = fillAdditionalInfo(customUserDetails);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    private CustomUserDetails getCustomUserDetails(Object principal) {
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        } else if (principal instanceof String) {
            return (CustomUserDetails) customUserDetailsService.loadUserByUsername((String) principal);
        }
        throw new IllegalArgumentException("Principal can only be of type CustomUserDetails or String");
    }

    private Map<String, Object> fillAdditionalInfo(CustomUserDetails customUserDetails) {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("userSeq", customUserDetails.getUserId());

        return additionalInfo;
    }
}

