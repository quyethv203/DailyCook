package com.example.foodwed.configuration;

import com.example.foodwed.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINS_POST = {"/auth/signup", "/auth/token", "/auth/introspect"};
    private final String[] PUBLIC_ENDPOINS_GET = {"/category","/suggestion/**","foodwed/images/**", "/recipe/recipeAll","/search/**"};
    private final String[] USER_AUTHEN_GET = {"/foodwed/order/uorder/**"};
    private final String[] ADMIN_AUTHEN_GET = {"foodwed/recipe","/foodwed/order"};
    private final String[] ADMIN_AUTHEN_POST = {"/foodwed/recipe/create","/foodwed/category/create"};
    private final String[] ADMIN_AUTHEN_PUT = {"/foodwed/recipe/update","/foodwed/category/update"};
    private final String[] ADMIN_AUTHEN_DELETE = {"/foodwed/recipe/delete","/foodwed/category/delete"};
    private String signerKey = "GtuAkpoXNfZOhcfdgkDJQ+N1Pd1pDwlc0syKYXZPQJT2ZI+mlWkd8Go5XL6rz93j";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINS_POST).permitAll()
                        .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINS_GET).permitAll()

                        // POST /foodwed/comments requires authentication (logged-in users only)
                        .requestMatchers(HttpMethod.POST, "/foodwed/comments").authenticated()
                        .requestMatchers(HttpMethod.GET, USER_AUTHEN_GET)
                        .hasAnyAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.GET, ADMIN_AUTHEN_GET)
                        .hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, ADMIN_AUTHEN_POST)
                        .hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,ADMIN_AUTHEN_PUT)
                        .hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,ADMIN_AUTHEN_DELETE)
                        .hasAnyAuthority(Role.ADMIN.name())
                        .anyRequest().authenticated());
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter())));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(),"HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    // Phương thức cấu hình CORS
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));  // Cấu hình domain của frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);  // Cho phép gửi cookie và thông tin xác thực
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Áp dụng cho tất cả các endpoint
        return source;
    }
}
