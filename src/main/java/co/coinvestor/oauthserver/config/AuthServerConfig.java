package co.coinvestor.oauthserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthServerConfig
        extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final CustomClientDetailsService customClientDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService);

//        clients.inMemory()
//                .withClient("client2")
//                .secret(passwordEncoder.encode("secret"))
//                .redirectUris("http://localhost:9090/home")
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
//                .scopes("ADMIN", "USER")
//                .and()
//                .withClient("resourceserver")
//                .secret(passwordEncoder.encode("resourceserversecret"));
    }

//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            clients.inMemory()
//                    .withClient("client2")
//                .secret(passwordEncoder.encode("secret"))
//            .redirectUris("http://localhost:9090/home")
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
//                .scopes("ADMIN","USER")
//                .and()
//                .withClient("resourceserver")
//                .secret(passwordEncoder.encode("resourceserversecret"));
//
//        var service = new InMemoryClientDetailsService();
//
//        var cd = new BaseClientDetails();
//        cd.setClientId("client");
//        cd.setClientSecret("secret");
//        cd.setScope(List.of("read"));
//        cd.setAuthorizedGrantTypes(List.of("password"));
//
//        service.setClientDetailsStore(Map.of("client", cd));
//
//        clients.withClientDetails(service);
//    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()");
    }
}
