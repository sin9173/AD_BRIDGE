package com.adbridge.config;

import com.adbridge.auth.AuthAccessDeniedHandler;
import com.adbridge.auth.AuthEntryPoint;
import com.adbridge.auth.CustomLogoutHandler;
import com.adbridge.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final AuthEntryPoint authEntryPoint;
    private final AuthAccessDeniedHandler authAccessDeniedHandler;

    private final CustomLogoutHandler customLogoutHandler;

    private final AuthorizationFilter authorizationFilter;

    /** 유저 권한 설정 */
    @Order(10)
    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/**");
        http.authorizeHttpRequests((auth) -> auth
                /** AUTH BASE PERMIT ALL */
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/adbridge/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                .requestMatchers(HttpMethod.GET, "/token/renew").permitAll()

                /** Server Running Check */
                .requestMatchers("/status/check").permitAll()

                /** BASIC ERROR */
                .requestMatchers("/error").permitAll()

                /** MAIN PAGE PERMIT ALL */
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated());

        basicSecurity(http);

        return http.getOrBuild();
    }

    /** 관리자 권한 설정 */
    @Order(1)
    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/admin/**");
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated());
        basicSecurity(http);
        return http.getOrBuild();
    }

    /** 기본 설정 */
    private void basicSecurity(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(c -> c
                .authenticationEntryPoint(authEntryPoint)
                .accessDeniedHandler(authAccessDeniedHandler));

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout((logout) -> logout
                .logoutUrl("/logout")
                .addLogoutHandler(customLogoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)).permitAll());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
