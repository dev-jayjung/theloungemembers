package com.theloungemembers.web.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 로컬 테스트용 (필요 시 활성화)
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // iFrame/탭 허용
//            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // iFrame/탭 허용
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll() // 🎯 모든 요청(URL)을 인증 없이 전면 허용!
                );
//            .authorizeHttpRequests(auth -> auth
//                // CSS, JS, 이미지 등 정적 리소스와 로그인 페이지는 인증 없이 허용
//                .requestMatchers("/css/**", "/js/**", "/images/**", "/login", "/error").permitAll()
//                // 그 외 모든 어드민 요청은 인증(로그인) 필수!
//                .anyRequest().authenticated()
//            )
//            .formLogin(form -> form
//                .loginPage("/login")             // 🎯 커스텀 로그인 페이지 URL
//                .loginProcessingUrl("/login-proc") // 🎯 HTML form action URL
//                .defaultSuccessUrl("/main", true)    // 로그인 성공 시 이동할 기본 메인 페이지
//                .usernameParameter("username")   // form 내 input name
//                .passwordParameter("password")
//                .permitAll()
//            )
//            .logout(logout -> logout
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .permitAll()
//            );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234")) // 임시 비밀번호
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}