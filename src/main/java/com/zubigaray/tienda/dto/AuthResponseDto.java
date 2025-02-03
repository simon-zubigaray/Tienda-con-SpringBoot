package com.zubigaray.tienda.dto;

import com.zubigaray.tienda.enums.AuthStatus;

/**
 * Representa una respuesta de autenticación que contiene un token de acceso, el estado de la autenticación y un mensaje descriptivo.
 * Este objeto es inmutable y se utiliza para enviar información de autenticación después de un proceso de inicio de sesión o registro.
 *
 * @param token      El token de acceso generado para el usuario autenticado. Puede ser {@code null} si la autenticación falla.
 * @param authStatus El estado de la autenticación, que indica si fue exitosa o si ocurrió algún error.
 * @param message    Un mensaje descriptivo que proporciona detalles adicionales sobre el resultado de la operación de autenticación.
 */
public record AuthResponseDto(
        String token,
        AuthStatus authStatus,
        String message
) {
}
