package annie470.U5_W3_D1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception  {
        httpSecurity.formLogin(formLogin -> formLogin.disable());
        httpSecurity.csrf((csrf -> csrf.disable()));
        httpSecurity.sessionManagement((sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS)));
        httpSecurity.authorizeHttpRequests(req -> req.requestMatchers("/**").permitAll());
        return httpSecurity.build();
    }
}
