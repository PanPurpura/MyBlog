package com.myblog.blog.configuration;

import com.myblog.blog.handler.CustomAccessDeniedHandler;
import com.myblog.blog.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static com.myblog.blog.model.Permission.*;
import static com.myblog.blog.model.Role.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final HandlerExceptionResolver exceptionResolver;

    public SecurityConfig(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver_,
            JwtAuthenticationFilter jwtAuthFilter_,
            AuthenticationProvider authenticationProvider_
    ) {
        this.exceptionResolver = exceptionResolver_;
        this.jwtAuthFilter = jwtAuthFilter_;
        this.authenticationProvider = authenticationProvider_;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers("api/v1/auth/**").permitAll()

                            .requestMatchers("/api/v1/user/**").hasAnyRole(ADMIN.name(), USER.name(), MODERATOR.name())

                            .requestMatchers(HttpMethod.GET, "api/v1/users/**").hasAnyAuthority(ADMIN_READ.getPermission(), MODERATOR_READ.getPermission())
                            .requestMatchers(HttpMethod.PATCH, "api/v1/users/**").hasAuthority(USER_UPDATE.getPermission())
                            .requestMatchers(HttpMethod.PUT, "api/v1/users/**").hasAuthority(USER_UPDATE.getPermission())
                            .requestMatchers(HttpMethod.DELETE, "api/v1/users/**").hasAuthority(USER_DELETE.getPermission())

                            .requestMatchers("/api/v1/user/**").hasAnyRole(ADMIN.name(), USER.name(), MODERATOR.name())

                            .requestMatchers(HttpMethod.GET, "api/v1/profile-images/**").hasAnyAuthority(ADMIN_READ.getPermission(), MODERATOR_READ.getPermission())
                            .requestMatchers(HttpMethod.PATCH, "api/v1/profile-images/**").hasAuthority(USER_UPDATE.getPermission())
                            .requestMatchers(HttpMethod.POST, "api/v1/profile-images/**").hasAuthority(USER_WRITE.getPermission())
                            .requestMatchers(HttpMethod.DELETE, "api/v1/profile-images/**").hasAuthority(USER_DELETE.getPermission())

                            .anyRequest()
                            .authenticated();
                })
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint(exceptionResolver));
                    ex.accessDeniedHandler(new CustomAccessDeniedHandler(exceptionResolver));
                })
                .sessionManagement(request -> {
                    request.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

}
