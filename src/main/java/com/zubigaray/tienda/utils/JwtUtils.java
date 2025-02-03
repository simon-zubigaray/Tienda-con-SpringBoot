package com.zubigaray.tienda.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Utilidades para trabajar con tokens JWT (JSON Web Tokens).
 * Esta clase proporciona métodos para generar, validar y extraer información de tokens JWT.
 */
//@Slf4j
public class JwtUtils {

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build(); // Clave secreta para firmar y verificar tokens
    private static final String ISSUER = "server"; // Emisor del token
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class); // Lo tuve que poner manualmente porque no esta funcionando correctamente lombok en IntelliJ, lanza errores del tipo "java: cannot find symbol"

    /**
     * Constructor privado para evitar la instanciación de la clase.
     * Todos los métodos son estáticos.
     */
    private JwtUtils() {
    }

    /**
     * Valida un token JWT verificando su firma y estructura.
     *
     * @param jwtToken El token JWT a validar.
     * @return true si el token es válido; false en caso contrario.
     */
    public static boolean validateToken(String jwtToken) {
        return parseToken(jwtToken).isPresent();
    }

    /**
     * Parsea un token JWT y extrae sus claims (información contenida en el token).
     *
     * @param jwtToken El token JWT a parsear.
     * @return Un Optional que contiene los claims del token si es válido; de lo contrario, un Optional vacío.
     */
    public static Optional<Claims> parseToken(String jwtToken) {
        var jwtParser = Jwts.parser().verifyWith(secretKey).build();

        try {
            return Optional.of(jwtParser.parseSignedClaims(jwtToken).getPayload());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT Exception occurred: " + e.getMessage());
            //System.err.println("JWT Exception occurred: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     *
     * @param jwtToken El token JWT del cual extraer el nombre de usuario.
     * @return Un Optional que contiene el nombre de usuario si el token es válido; de lo contrario, un Optional vacío.
     */
    public static Optional<String> getUserNameFromToken(String jwtToken) {
        var claimsOptional = parseToken(jwtToken);
        return claimsOptional.map(Claims::getSubject);
    }

    /**
     * Genera un nuevo token JWT para un nombre de usuario específico.
     * El token incluye un ID único, un emisor (issuer), un subject (nombre de usuario),
     * una fecha de emisión y una fecha de expiración.
     *
     * @param userName El nombre de usuario para el cual generar el token.
     * @return Un token JWT firmado.
     */
    public static String generateToken(String userName) {
        var currentDate = new Date();
        var jwtExpirationInMinutes = 10; // Tiempo de expiración del token en minutos
        var expiration = DateUtils.addMinutes(currentDate, jwtExpirationInMinutes);

        return Jwts.builder()
                .id(UUID.randomUUID().toString()) // ID único para el token
                .issuer(ISSUER) // Emisor del token
                .subject(userName) // Nombre de usuario (subject)
                .signWith(secretKey) // Firma del token con la clave secreta
                .issuedAt(currentDate) // Fecha de emisión
                .expiration(expiration) // Fecha de expiración
                .compact(); // Construye y devuelve el token como una cadena compacta
    }
}
