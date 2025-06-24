package com.flowreserve.demo1.config;
import com.flowreserve.demo1.config.filter.JwtTokenValidator;
import com.flowreserve.demo1.service.user.UserDetailServiceImpl;
import com.flowreserve.demo1.utils.JWTUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
@Autowired
    private JWTUtils jwtUtils;

    // Configuración de seguridad HTTP
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
                        .requestMatchers("/api/v1/hospital/**").permitAll()
                        .requestMatchers("/api/v1/invitaciones/**").permitAll()
                        .requestMatchers("/api/v1/medicos/**").permitAll()
                        .requestMatchers("/api/v1/pacientes/**").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.POST, "/Auth/**").permitAll()
                        .requestMatchers("/api/v1/solicitudes/**").authenticated() //
                        //.anyRequest().authenticated()
                        .anyRequest().permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint())
                )
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class);


        return http.build();
    }

    // AuthenticationManager necesario si haces login manual (opcional si usas formulario)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // permite el uso de cookies (withCredentials)
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // dirección provisional del front-end
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        config.setExposedHeaders(Arrays.asList("Authorization")); // opcional

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    // Codificador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Acceso denegado: no tiene permisos suficientes.\"}");
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"No autenticado: debe iniciar sesión.\"}");
        };
    }

//    @Bean
//    @Order(1)
//    public SecurityFilterChain basicAuthFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/api/v1/**") // ⬅️ Aplica Basic Auth a cualquier ruta bajo /api/v1
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/api/v1/basic-secure/**",
//                                "/api/v1/solicitudes/estado-actual",
//                                "/api/v1/solicitudes/*/estado"  // ⬅️ incluye el PUT /{id}/estado
//                        ).authenticated()
//                        .anyRequest().permitAll()
//                )
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }



//    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("12345"));
//    }

}
