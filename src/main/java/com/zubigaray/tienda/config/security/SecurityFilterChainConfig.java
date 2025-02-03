package com.zubigaray.tienda.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configura la cadena de filtros de seguridad (SecurityFilterChain) para la aplicación.
 * Esta clase define las reglas de seguridad, como la deshabilitación de CORS y CSRF,
 * la configuración de autorizaciones para endpoints específicos, el manejo de excepciones
 * de autenticación y la adición de filtros personalizados.
 */
@Configuration
public class SecurityFilterChainConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructor para la clase SecurityFilterChainConfig.
     *
     * @param authenticationEntryPoint Punto de entrada para manejar excepciones de autenticación.
     * @param jwtAuthenticationFilter  Filtro personalizado para la autenticación basada en JWT.
     */
    public SecurityFilterChainConfig(AuthenticationEntryPoint authenticationEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configura y devuelve un bean de tipo SecurityFilterChain.
     * Este método define las reglas de seguridad para la aplicación, incluyendo:
     * - Deshabilitación de CORS y CSRF.
     * - Autorización de endpoints públicos (como login y registro).
     * - Exigencia de autenticación para el resto de los endpoints.
     * - Manejo de excepciones de autenticación.
     * - Configuración de la política de creación de sesiones.
     * - Adición de un filtro personalizado para la autenticación JWT.
     *
     * @param httpSecurity Objeto HttpSecurity utilizado para configurar la seguridad.
     * @return Un objeto SecurityFilterChain configurado.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Deshabilita CORS y CSRF
        httpSecurity.cors(AbstractHttpConfigurer::disable);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // Configura las reglas de autorización para los endpoints
        httpSecurity.authorizeHttpRequests(
                requestMatcher -> requestMatcher
                        .requestMatchers("/api/auth/login/**").permitAll() // Permite acceso público al endpoint de login
                        .requestMatchers("/api/auth/register/**").permitAll() // Permite acceso público al endpoint de registro
                        .anyRequest().authenticated() // Exige autenticación para cualquier otro endpoint
        );

        // Configura el manejo de excepciones de autenticación
        httpSecurity.exceptionHandling(
                exceptionConfig -> exceptionConfig.authenticationEntryPoint(authenticationEntryPoint)
        );

        // Configura la política de creación de sesiones
        httpSecurity.sessionManagement(
                sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        );

        // Agrega el filtro personalizado de JWT antes del filtro de autenticación de usuario y contraseña
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Construye y devuelve la cadena de filtros de seguridad
        return httpSecurity.build();
    }
}
