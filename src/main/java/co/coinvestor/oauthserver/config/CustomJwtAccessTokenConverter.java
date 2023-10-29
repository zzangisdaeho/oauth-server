package co.coinvestor.oauthserver.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

//    @Override
//    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//        OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
//
//        if (enhancedToken instanceof DefaultOAuth2AccessToken) {
//            DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) enhancedToken;
//            token.getAdditionalInformation().remove("user_name");
//        }
//
//        return enhancedToken;
//    }
//
//    @Override
//    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
//        Map<String, Object> response = (Map<String, Object>) super.convertAccessToken(token, authentication);
//        response.remove("user_name");
//        return response;
//    }
}
