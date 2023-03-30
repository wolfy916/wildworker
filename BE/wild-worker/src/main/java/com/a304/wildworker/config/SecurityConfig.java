package com.a304.wildworker.config;

import com.a304.wildworker.auth.CustomLoginSuccessHandler;
import com.a304.wildworker.auth.CustomLogoutHandler;
import com.a304.wildworker.auth.CustomOAuth2UserService;
import com.a304.wildworker.common.Constants;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig<S extends Session> {

    private final CustomLogoutHandler logoutHandler;
    private final CustomLoginSuccessHandler loginSuccessHandler;
    private final CustomOAuth2UserService oAuth2UserService;
    @Value("${allowed-origins}")
    private final String[] allowedOrigins;
    private final FindByIndexNameSessionRepository<S> sessionRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false) //true: 동시 로그인 차단, false: 기존 세션 만료
                .sessionRegistry(sessionRegistry());

        http
                .cors()
                .configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(
                            List.of(allowedOrigins));
                    cors.setAllowedMethods(
                            List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(
                            List.of("DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Cookie,Content-Type,Accept,Origin,Authorization"));
                    cors.setAllowCredentials(true);
                    return cors;
                })
                .and()
                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

        http
                .csrf().disable()   //TODO. csrf disable 안 하고 처리
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeHttpRequests()
                .antMatchers("/ws/**").authenticated()
                .antMatchers("/secured/ws/**").authenticated()
                .antMatchers("/auth/login", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(
                        HttpStatus.OK))
                .invalidateHttpSession(true)
                .deleteCookies(Constants.KEY_SESSION_ID)
                .and()
                .formLogin().disable()
                .oauth2Login()
                .successHandler(loginSuccessHandler)
                .userInfoEndpoint()
                .userService(oAuth2UserService);

        http
                // ignore our stomp endpoints since they are protected using Stomp headers
                .headers()
                // allow same origin to frame our site to support iframe SockJS
                .frameOptions().sameOrigin();

        return http.build();
    }

    @Bean
    public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
    }
}