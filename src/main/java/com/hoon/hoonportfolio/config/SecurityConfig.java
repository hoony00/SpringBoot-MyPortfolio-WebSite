package com.hoon.hoonportfolio.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
스프링 시큐리티를 사용하면 기본적인 시큐리티 설정을 하기 위해서
WebSecurityConfigurerAdapter라는 추상 클래스를 상속하고,
configure 메서드를 오버라이드하여 설정하였습니다.
그러나 스프링 시큐리티 5.7.0-M2 부터 WebSecurityConfigurerAdapter는
deprecated 되었습니다.


스프링 공식 블로그 2022년 2월 21일 글에서 WebSecurityConfigurerAdapter를
사용하는 것을 권장하지 않는다고 컴포넌트 기반 설정으로 변경할것을 권항합니다.

스프링 부트 2.7.0 이상의 버전을 사용하면 스프링 시큐리티 5.7.0 혹은
이상의 버전과 의존성이 있습니다.
그렇다면 WebSecurityConfigurerAdapter가 deprecated 된 것을 확인할 수 있습니다.
현재 스프링 부트 3와 의존관계인 스프링 시큐리티6에서는 WebSecurityConfigurerAdapter
클래스가 제거되었습니다.
스프링 부트 혹은 스프링 시큐리티 버전을 높이기 위해서라면
WebSecurityConfigurerAdapter deprecated 된 설정을 제거해야 합니다.
*/

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@AllArgsConstructor
@RequiredArgsConstructor
// Secured Annotation 활성화, preAuthorize Annotation 활성화
public class SecurityConfig {

/*
        @Bean   // @Bean의 역할은 해당 메서드의 return 되는 Object를 IoC로 등록해줌
*/


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         /*
        //http.addFilter(new Filter1());
        //http.addFilterBefore(new Filter3(), BasicAuthenticationFilter.class);  // 기본적으로 이렇게 건다
        // 사용자가 임의로 지정한 Filter는 springSecurityFilterChain에 등록이 되지 않는다.(타입이 Filter이기 때문)
        // 따라서 해당 Filter를 사용하기 위해서는 addFilterAfter 또는 addFilterBefore를 통해서 연계를 시켜줘야 한다.
        http.csrf(CsrfConfigurer::disable);
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Session 사용 X
        }).addFilter(corsFilter)    // @CrossOrigin은 인증이 필요없을때 사용하지만, 그게 아니라면 필터에 등록을 해줘야 한다.
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpSecurityHttpBasicConfigurer ->httpSecurityHttpBasicConfigurer.disable()
                // httpBasic 방식은 headers의 Authorization의 값으로 ID와 PW를 포함해서 request를 보내는데
                // 이 방식대로 하면 ID와 PW가 노출되기 때문에 보안에 상당한 취약점을 들어낸다.
                // 따라서 ID와 PW 대신에 Token을 사용하는 방식인 httpBearer 방식을 사용하는 것이 그나마 보안에 덜 취약하다.
                // (httpBearer 방식을 사용한다고 해서 Token이 노출이 안된다는 것은 아님.)
                // 이러한 방식이 JWT 인증 방식이다.
                // 즉, httpBearer방식을 사용하기 위해서 Session, formLogin, HttpBasic을 다 비활성화 시킴.
        ).apply(new MyCustomDs1());
         */
        //http.addFilterBefore(new Filter3(), BasicAuthenticationFilter.class);  // 기본적으로 이렇게 건다

        http.formLogin(form -> form
                .loginPage("/user/login") // 로그인 페이지
                .defaultSuccessUrl("/user/loginAction") // 로그인 성공 후 이동 페이지
                .failureUrl("/user/login/error")
                .usernameParameter("email") // 로그인 페이지의 아이디 파라미터
                .passwordParameter("password") // 로그인 페이지의 비밀번호 파라미터
                .permitAll() //모든 사용자가 접근할 수 있음
        ); //formLogin



        http.logout(Customizer.withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request // 인가 정책
                        .requestMatchers("/bootstrap/**", "/images/**","/js/**").permitAll()
                        .requestMatchers("/",  "/user/**", "/layout/**", "/images/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                // rest api에서는 권한이 필요한 요청 위해서 인증 정보를 포함시켜야 하나 서버에 인증정보를 저장하지 않기 때문에 csrf를 비활성화 시킴 (jwt를 쿠키에 저장 x)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);



        return http.build();
    } //filterChain

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring()
                .requestMatchers("/error/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
