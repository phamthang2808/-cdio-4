package com.project.cdio.config;


import com.project.cdio.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
//@EnableMethodSecurity
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    String.format("%s/users/login", apiPrefix),
                                    String.format("%s/users/register", apiPrefix)
                            )
                            .permitAll()
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/roles**",apiPrefix)).permitAll()

                            .requestMatchers(GET,String.format("%s/rooms/**", apiPrefix)).permitAll()
                            .requestMatchers(POST,String.format("%s/rooms", apiPrefix)).permitAll()  // ......
                            .requestMatchers(GET,String.format("%s/rooms/staff", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/users/**",apiPrefix)).permitAll()

//                           permitAll = không cần đăng nhập, không cần token.
//
//                           authenticated = cần đăng nhập, cần token hợp lệ (role gì cũng được).
//
//                           hasRole("STAFF") = cần token hợp lệ + chứa role STAFF.

                            .requestMatchers(String.format("%s/customers/**", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/customers/staff", apiPrefix)).hasRole("STAFF")

                            .requestMatchers(GET,
                                    String.format("%s/healthcheck/**", apiPrefix)).permitAll()
                            .requestMatchers("/error", "/favicon.ico", "/css/**", "/js/**", "/images/**").permitAll()
                            .requestMatchers( "/uploads/**").permitAll()
                            .requestMatchers( "/uploads/**", apiPrefix).permitAll()
                            .requestMatchers(String.format("%s/rooms/**", apiPrefix)).permitAll()

//                            .requestMatchers(String.format("%s/rooms/**", apiPrefix)).hasRole("STAFF")
//                            .requestMatchers(HttpMethod.POST, String.format("%s/rooms/**", apiPrefix)).hasRole("STAFF")
//                            .requestMatchers(HttpMethod.PUT, String.format("%s/rooms/**", apiPrefix)).hasRole("STAFF")
//                            .requestMatchers(HttpMethod.DELETE, String.format("%s/rooms/**", apiPrefix)).hasRole("STAFF")

//                            .requestMatchers(String.format("%s/users/**", apiPrefix)).hasRole("STAFF")

                            // ====== ADMIN ======
//                            .requestMatchers(String.format("%s/roles/**", apiPrefix)).hasRole("ADMIN")

                            .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable);
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return http.build();
    }
}
