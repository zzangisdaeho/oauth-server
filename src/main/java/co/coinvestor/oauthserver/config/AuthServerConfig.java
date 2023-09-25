package co.coinvestor.oauthserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig
        extends AuthorizationServerConfigurerAdapter {

    private final String password;
    private final String privateKey;
    private final String alias;
    private final AuthenticationManager authenticationManager;
    private final CustomClientDetailsService customClientDetailsService;
    private final DataSource dataSource;
    private final CustomTokenEnhancer customTokenEnhancer;

    public AuthServerConfig(@Value("${rsa.password}") String password,
                            @Value("${rsa.privateKey}") String privateKey,
                            @Value("${rsa.alias}") String alias,
                            AuthenticationManager authenticationManager,
                            CustomClientDetailsService customClientDetailsService,
                            DataSource dataSource,
                            CustomTokenEnhancer customTokenEnhancer) {
        this.password = password;
        this.privateKey = privateKey;
        this.alias = alias;
        this.authenticationManager = authenticationManager;
        this.customClientDetailsService = customClientDetailsService;
        this.dataSource = dataSource;
        this.customTokenEnhancer = customTokenEnhancer;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenServices(tokenServices())
                .approvalStore(approvalStore())
                .pathMapping("/oauth/confirm_access", "/custom/oauth/approve")
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                //check_token endpoint (credential 필수)
                .checkTokenAccess("isAuthenticated()")
                //token_key endpoint (credential 필수)
                .tokenKeyAccess("permitAll()");
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public DefaultTokenServices tokenServices() {

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds(3600); // set access token to expire after 1 hour
        tokenServices.setRefreshTokenValiditySeconds(86400); // set refresh token to expire after 1 day

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(List.of(customTokenEnhancer, jwtAccessTokenConverter()));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        return tokenServices;
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var converter = new JwtAccessTokenConverter();

        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(
                        new ClassPathResource(privateKey),
                        password.toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));

        return converter;
    }



}
