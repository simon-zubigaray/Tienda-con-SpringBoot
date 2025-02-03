package com.zubigaray.tienda.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Implementación personalizada de {@link AuthenticationEntryPoint} que se utiliza para manejar
 * excepciones de autenticación. Cuando un usuario no autenticado intenta acceder a un recurso protegido,
 * este componente envía una respuesta de error HTTP 401 (Unauthorized) con el mensaje de la excepción.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Método que se ejecuta cuando se produce una excepción de autenticación.
     * Este método envía una respuesta de error HTTP 401 (Unauthorized) al cliente,
     * incluyendo el mensaje de la excepción.
     *
     * @param httpServletRequest         La solicitud HTTP que causó la excepción.
     * @param httpServletResponse        La respuesta HTTP que se enviará al cliente.
     * @param authenticationException    La excepción de autenticación que se produjo.
     * @throws IOException               Si ocurre un error de entrada/salida al enviar la respuesta.
     * @throws ServletException          Si ocurre un error relacionado con el servlet.
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authenticationException)
            throws IOException, ServletException {
        httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), authenticationException.getMessage());
    }
}
