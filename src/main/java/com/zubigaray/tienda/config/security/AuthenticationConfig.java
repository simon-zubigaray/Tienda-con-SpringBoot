package com.zubigaray.tienda.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración de Spring para la autenticación en la aplicación.
 * Esta clase define los beans necesarios para gestionar la autenticación, incluyendo
 * el AuthenticationManager, el AuthenticationProvider y el PasswordEncoder.
 */
@Configuration
public class AuthenticationConfig {

    /**
     * Expone un bean de tipo AuthenticationManager, que es responsable de gestionar
     * el proceso de autenticación en Spring Security.
     *
     * @param authenticationConfiguration Configuración de autenticación proporcionada por Spring Security.
     * @return Una instancia de AuthenticationManager configurada.
     * @throws Exception Si ocurre un error al obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura y expone un bean de tipo AuthenticationProvider, específicamente un
     * DaoAuthenticationProvider, que autentica usuarios utilizando un UserDetailsService
     * y un PasswordEncoder.
     *
     * @param userDetailsService Servicio para cargar los detalles del usuario (por ejemplo, desde una base de datos).
     * @param passwordEncoder    Codificador de contraseñas para verificar la contraseña proporcionada.
     * @return Una instancia de DaoAuthenticationProvider configurada.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        var authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }

    /**
     * Expone un bean de tipo PasswordEncoder, específicamente un BCryptPasswordEncoder,
     * que se utiliza para codificar y verificar contraseñas de manera segura.
     *
     * @return Una instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
