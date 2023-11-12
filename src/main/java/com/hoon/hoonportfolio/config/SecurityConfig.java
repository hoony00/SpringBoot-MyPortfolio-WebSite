package com.hoon.hoonportfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(form -> form
                .loginPage("/user/login") // 로그인 페이지
                .defaultSuccessUrl("/") // 로그인 성공 후 이동 페이지
                .failureUrl("/user/login/error")
                .usernameParameter("email") // 로그인 페이지의 아이디 파라미터
                .passwordParameter("password") // 로그인 페이지의 비밀번호 파라미터
                .permitAll() //모든 사용자가 접근할 수 있음
        ); //formLogin


        http.logout(Customizer.withDefaults()); // 로그아웃 설정

        http.authorizeHttpRequests(request -> request // 인가 정책
                // 루트와 /member/** 경로는 모든 사용자가 접근 가능
                .requestMatchers("/bootstrap/**").permitAll()
                .requestMatchers("/", "/other-public-pages", "/user/**", "/layout/**", "/images/**").permitAll()
                .anyRequest().authenticated()); //authorizeHttpRequests


        return http.build();
    } //filterChain

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
