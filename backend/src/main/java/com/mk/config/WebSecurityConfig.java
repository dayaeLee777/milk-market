package com.mk.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        .antMatchers("/h2-console/**")
        .antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/swagger*/**",
                "/configuration/**",
                "/configuration/security",
                "/swagger-ui/",
                "/swagger-ui.html",
                "/swagger-ui/index.html",
                "/webjars/**");  // --> 추가
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
                .csrf().disable() // csrf 보안 토큰 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
            .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/", "/css/**", "/images/**", "/js/**",
                        "/h2-console/**", "/profile","/api/email/**","/api/users/login","/api/users/signup/**",
                        "/exception/**","/search/**","/users/findpwd/**", "/api/oauth/**", "/api/item/list/**", "/api/interest/hotitem/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
//예외처리 필요하면 추가
//            .and()
//                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//            .and()
//                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTION"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
