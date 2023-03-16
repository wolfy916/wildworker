package com.a304.wildworker.config;

import com.a304.wildworker.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
//    private final OAuthService oAuthService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/", "/index", "/oauth2/**").permitAll()
//                .hasRole(Role.ROLE_ANONYMOUSE.toString())
                .anyRequest().authenticated()
//                .anyRequest().permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()// OAuth2 로그인 설정 시작
//                .defaultSuccessUrl("/home")
                .userInfoEndpoint()  // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정을 저장
                .userService(oAuth2UserService);// OAuth2 로그인 성공 시, 후작업을 진행할 UserService 인터페이스 구현체 등록

        return http.build();
    }
}