package com.zubigaray.tienda.dto;

/**
 * Representa una solicitud de autenticación con los datos necesarios para el registro o inicio de sesión.
 * Este objeto es inmutable y contiene información básica del usuario.
 *
 * @param name     El nombre completo del usuario.
 * @param userName El nombre de usuario único para identificar al usuario en el sistema.
 * @param password La contraseña del usuario para autenticación.
 * @param mail     El correo electrónico del usuario.
 */
public record AuthRequestDto(
        String name,
        String userName,
        String password,
        String mail
) {
}
