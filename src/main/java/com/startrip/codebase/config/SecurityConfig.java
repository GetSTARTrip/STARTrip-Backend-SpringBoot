package com.startrip.codebase.config;

import com.startrip.codebase.domain.auth.CustomAccessDeniedHandler;
import com.startrip.codebase.domain.auth.CustomAuthenticationSuccessHandler;
import com.startrip.codebase.domain.auth.CustomOAuth2UserService;
import com.startrip.codebase.jwt.JwtAccessDeniedHandler;
import com.startrip.codebase.jwt.JwtAuthenticationEntryPoint;
import com.startrip.codebase.jwt.JwtSecurityConfig;
import com.startrip.codebase.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 메소드 별로 인증 처리
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private String[] permitList = {
        "/v2/**",
        "/configuration/**",
        "/swagger*/**",
        "/webjars/**",
        "/swagger-resources/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/css/**, /static/js/**, *.ico");

        // swagger
        web.ignoring().antMatchers(
            "/v2/api-docs", "/configuration/ui",
            "/swagger-resources", "/configuration/security",
            "/swagger-ui.html", "/webjars/**", "/swagger/**");

        web.ignoring().antMatchers(permitList);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
            .csrf().disable()

            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            // enable h2-console
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            // 세션을 사용하지 않기 때문에 STATELESS로 설정
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/api/user/signup", "api/user/login").permitAll()
            .antMatchers(permitList).permitAll()

            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .apply(new JwtSecurityConfig(tokenProvider));

        httpSecurity
            .oauth2Login()
            .userInfoEndpoint().userService(customOAuth2UserService)
            .and()
            .successHandler(customAuthenticationSuccessHandler)
            .permitAll();

        //.authorizationRequestRepository()
    }


}