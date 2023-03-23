package com.a304.wildworker.config;

import com.a304.wildworker.config.service.CustomLoginSuccessHandler;
import com.a304.wildworker.config.service.CustomLogoutHandler;
import com.a304.wildworker.config.service.CustomOAuth2UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomLogoutHandler logoutHandler;
    private final CustomLoginSuccessHandler loginSuccessHandler;
    private final CustomOAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("*"));
                    cors.setAllowedMethods(
                            List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(
                            List.of("Content-Type", "x-requested-with", "Authorization",
                                    "Access-Control-Allow-Origin"));
                    return cors;
                })
                .and()
                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

        http
                .csrf().disable()   //TODO. csrf disable 안 하고 처리
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeHttpRequests()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/secured/ws/**").authenticated()
                .antMatchers("/auth/login", "/oauth2/**").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(
                        HttpStatus.OK))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .formLogin().disable()
                .oauth2Login()
                .successHandler(loginSuccessHandler)
                .userInfoEndpoint()
                .userService(oAuth2UserService);

        http
                .csrf()
                // ignore our stomp endpoints since they are protected using Stomp headers
                .ignoringAntMatchers("/ws/**")
                .and()
                .headers()
                // allow same origin to frame our site to support iframe SockJS
                .frameOptions().sameOrigin();

        return http.build();
    }
}