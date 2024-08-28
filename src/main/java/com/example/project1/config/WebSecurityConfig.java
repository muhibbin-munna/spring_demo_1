package com.example.project1.config;

import com.example.project1.security.JwtAuthenticationEntryPoint;
import com.example.project1.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebSecurity
@Configuration
@EnableWebMvc
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    private static final String[] WHITE_LIST_URLS = {
            "/",
            "/register",
            "/login",
            "/items",
//            "/v3/api-docs",
//            "/v3/api-docs/**",
//            "/swagger-ui/**",
//            "/swagger-ui.html"

    };
    private static final String[] NEED_ADMIN_URLS = {
            "/category/add",
            "/item/add/*",
            "/admin/register"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                        .cors(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(
                                        auth -> auth
                                                .requestMatchers(NEED_ADMIN_URLS).hasAuthority("ADMIN")
                                        .requestMatchers(WHITE_LIST_URLS).permitAll()

                                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
