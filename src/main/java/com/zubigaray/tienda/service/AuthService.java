package com.zubigaray.tienda.service;

/**
 * Servicio de autenticación que proporciona métodos para el inicio de sesión, registro y verificación de tokens.
 * Esta interfaz define las operaciones principales relacionadas con la autenticación de usuarios en el sistema.
 */
public interface AuthService {

    /**
     * Inicia sesión para un usuario con las credenciales proporcionadas.
     *
     * @param userName El nombre de usuario del usuario que intenta iniciar sesión.
     * @param password La contraseña del usuario.
     * @return Un token de autenticación si las credenciales son válidas, o un mensaje de error en caso contrario.
     */
    String login(String userName, String password);

    /**
     * Registra un nuevo usuario en el sistema con la información proporcionada.
     *
     * @param name     El nombre completo del usuario.
     * @param userName El nombre de usuario único para el nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     * @param email    El correo electrónico del nuevo usuario.
     * @return Un token de autenticación si el registro es exitoso, o un mensaje de error en caso contrario.
     */
    String signUp(String name, String userName, String password, String email);

    /**
     * Verifica la validez de un token de autenticación.
     *
     * @param token El token de autenticación a verificar.
     * @return Un mensaje que indica si el token es válido o no.
     */
    String verifyToken(String token);
}