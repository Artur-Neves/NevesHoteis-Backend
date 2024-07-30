package br.com.nevesHoteis.infra.config;

import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.infra.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.CompositeAccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static br.com.nevesHoteis.domain.Role.ADMIN;
import static br.com.nevesHoteis.domain.Role.USER;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthenticationConfig {
    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    SecurityFilterChain config (HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(req -> {
                        req.requestMatchers(HttpMethod.GET, "/user/**", "/hotel/**").permitAll();
                        req.requestMatchers(HttpMethod.POST, "/user/login", "/simple-user/create-simple", "/user/refresh", "/email-token/**").permitAll();
                        req.requestMatchers(HttpMethod.PUT, "/user/redefine-password").permitAll();
                        req.requestMatchers( "/admin/**", "/employee/**").hasAuthority("ADMIN");
                        req.requestMatchers("/hotel/**").hasAuthority("EMPLOYEE");
                        //req.requestMatchers("/booking/**").hasAuthority("USER");

                                req.anyRequest().authenticated();

                    })
                   .exceptionHandling(ex->{
                            ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                            ex.accessDeniedHandler(customAccessDeniedHandler());
                            })

                    .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }



    AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(403);
        };
    }
}
