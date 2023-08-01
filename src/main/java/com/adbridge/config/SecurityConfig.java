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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

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
        http.cors(cors -> cors.configurationSource(corsConfiguration()));
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


    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowCredentials(true);
        allowMethod(corsConfig, Arrays.asList("GET", "PATCH", "POST", "OPTIONS", "DELETE", "PUT"));
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://adbridge.net", "http://www.adbridge.net"));
        corsConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Access-Control-Allow-Credentials", "token", "Accept", "Authentication", "Request-Type"));
        corsConfig.setExposedHeaders(Arrays.asList("X-Get-Header", "Authentication", "token","Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Access-Control-Allow-Credentials"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }

//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        return new CorsWebFilter(corsConfiguration());
//    }

    private void allowMethod(CorsConfiguration config, List<String> list) {
        list.forEach(m -> config.addAllowedMethod(m));
    }
}
