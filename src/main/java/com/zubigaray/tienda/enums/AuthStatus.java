package com.zubigaray.tienda.enums;

/**
 * Representa los posibles estados de un proceso de autenticación.
 * Este enum se utiliza para indicar el resultado de operaciones como el registro de un nuevo usuario o el inicio de sesión.
 */
public enum AuthStatus {
    /**
     * Indica que un usuario fue creado exitosamente en el sistema.
     */
    USER_CREATED_SUCCESSFULLY,

    /**
     * Indica que ocurrió un error al intentar crear un nuevo usuario.
     */
    USER_NOT_CREATED,

    /**
     * Indica que el inicio de sesión fue exitoso.
     */
    LOGIN_SUCCESS,

    /**
     * Indica que el inicio de sesión falló debido a credenciales incorrectas u otros errores.
     */
    LOGIN_FAILED
}
