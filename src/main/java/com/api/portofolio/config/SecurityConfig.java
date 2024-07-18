package com.api.portofolio.config;

import com.api.portofolio.services.PortoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PortoUserDetailsService portoUserDetailsService;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        RequestMatcher swaggerUI = new AntPathRequestMatcher("/swagger-ui/**");
        RequestMatcher apiDocs = new AntPathRequestMatcher("/v3/api-docs/**");
        RequestMatcher swaggerUiHtml = new AntPathRequestMatcher("/swagger-ui.html");
        RequestMatcher publicEndpoints = new AntPathRequestMatcher("/auth/**");
        RequestMatcher adminEndpoints = new AntPathRequestMatcher("/admin/**");
        RequestMatcher userEndpoints = new AntPathRequestMatcher("/user/**");
        RequestMatcher adminUserEndpoints = new AntPathRequestMatcher("/adminuser/**");

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(swaggerUI).permitAll()
                        .requestMatchers(apiDocs).permitAll()
                        .requestMatchers(swaggerUiHtml).permitAll()
                        .requestMatchers(publicEndpoints).permitAll()
                        .requestMatchers(adminEndpoints).hasAnyAuthority("ADMIN")
                        .requestMatchers(userEndpoints).hasAnyAuthority("USER")
                        .requestMatchers(adminUserEndpoints).hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(portoUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
