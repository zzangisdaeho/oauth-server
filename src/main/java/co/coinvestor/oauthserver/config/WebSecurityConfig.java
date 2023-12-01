package co.coinvestor.oauthserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .permitAll()
        ;

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.logout().logoutSuccessUrl("/logout");
        http.authorizeRequests()
                .mvcMatchers("/error/**").permitAll()
                .mvcMatchers("/test/**").permitAll()
                .mvcMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated();
//        http.csrf().disable();
//        http.httpBasic();
//        http.authorizeRequests().anyRequest().permitAll();
    }

}
