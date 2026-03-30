package org.security.autoconfigure;

import org.security.filter.JwtAuthenticationFilter;
import org.security.jwt.JwtTokenProvider;
import org.security.logging.SecurityLoggingFilter;
import org.security.properties.SecurityProperties;
import org.security.securityErrors.JwtAccessDeniedHandler;
import org.security.securityErrors.JwtAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AutoConfiguration
@ConditionalOnClass(HttpSecurity.class)
@EnableMethodSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {

    @Bean
    public JwtTokenProvider jwtTokenProvider(SecurityProperties properties) {
        return new JwtTokenProvider(properties);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtTokenProvider tokenProvider,
            JwtAuthenticationEntryPoint authenticationEntryPoint,
            JwtAccessDeniedHandler accessDeniedHandler
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )

                .addFilterAfter(
                        new SecurityLoggingFilter(),
                        JwtAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}