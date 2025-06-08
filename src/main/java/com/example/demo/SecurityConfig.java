package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login-page", "/register", "/register/**", "/static/**", "/login").permitAll()
                .anyRequest().authenticated()
            )
            // 禁用默认表单登录 自定义处理post请求
            // .formLogin(form -> form.disable()
            .formLogin(form -> form
                .loginPage("/login-page")
                
                .defaultSuccessUrl("/index", true)
                .permitAll()
            )// 禁用CSRF
            .csrf(csrf -> csrf.disable());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}