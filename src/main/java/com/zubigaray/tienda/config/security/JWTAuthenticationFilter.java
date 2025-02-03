package com.zubigaray.tienda.config.security;

import com.zubigaray.tienda.utils.JwtUtils;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;


/**
 * Filtro personalizado que se ejecuta una vez por cada solicitud HTTP y se encarga de autenticar
 * a los usuarios basándose en un token JWT (JSON Web Token) proporcionado en el encabezado de la solicitud.
 * Este filtro verifica la validez del token, extrae el nombre de usuario y carga los detalles del usuario
 * para establecer la autenticación en el contexto de seguridad de Spring.
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Método principal que se ejecuta para cada solicitud HTTP. Este método:
     * 1. Extrae el token JWT del encabezado de la solicitud.
     * 2. Valida el token JWT.
     * 3. Extrae el nombre de usuario del token.
     * 4. Carga los detalles del usuario utilizando el UserDetailsService.
     * 5. Establece la autenticación en el contexto de seguridad de Spring.
     *
     * @param httpServletRequest  La solicitud HTTP entrante.
     * @param httpServletResponse La respuesta HTTP que se enviará al cliente.
     * @param filterChain         La cadena de filtros a la que se delega la solicitud.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        // Extrae el token JWT de la solicitud
        var jwtTokenOptional = getTokenFromRequest(httpServletRequest);

        // Si el token está presente y es válido, procede con la autenticación
        jwtTokenOptional.ifPresent(jwtToken -> {
            if (JwtUtils.validateToken(jwtToken)) {
                var userNameOptional = JwtUtils.getUserNameFromToken(jwtToken);

                // Si se puede extraer el nombre de usuario, carga los detalles del usuario y establece la autenticación
                userNameOptional.ifPresent(userName -> {
                    var userDetails = userDetailsService.loadUserByUsername(userName);
                    var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });
            }
        });

        // Continúa con la cadena de filtros
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * Extrae el token JWT del encabezado de autorización de la solicitud HTTP.
     * El token debe estar en el formato "Bearer <token>".
     *
     * @param httpServletRequest La solicitud HTTP de la cual extraer el token.
     * @return Un Optional que contiene el token JWT si está presente y en el formato correcto; de lo contrario, un Optional vacío.
     */
    private Optional<String> getTokenFromRequest(HttpServletRequest httpServletRequest) {
        var authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7)); // Extrae el token sin el prefijo "Bearer "
        }
        return Optional.empty();
    }
}
