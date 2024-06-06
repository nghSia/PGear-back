package com.jpo.pgearback.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class WebSecurityConfiguration implements WebMvcConfigurer{
    private final JwtRequestFilter v_authfilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity p_http) throws Exception {
        p_http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login", "/sign-up", "/order/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(v_authfilter, UsernamePasswordAuthenticationFilter.class);

        return p_http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public WebMvcConfigurer corsConfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry p_registry) {
                p_registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods(HttpMethod.GET.toString(), HttpMethod.PATCH.toString(),
                                HttpMethod.PUT.toString(), HttpMethod.DELETE.toString(),
                                HttpMethod.POST.toString(), HttpMethod.OPTIONS.toString())
                        .maxAge(3600)
                        .allowedHeaders("*");
            }
        };
    }
}
