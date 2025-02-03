package com.zubigaray.tienda.controller;

import com.zubigaray.tienda.dto.AuthRequestDto;
import com.zubigaray.tienda.dto.AuthResponseDto;
import com.zubigaray.tienda.enums.AuthStatus;
import com.zubigaray.tienda.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador REST para manejar las solicitudes relacionadas con la autenticación de usuarios.
 * Proporciona endpoints para el inicio de sesión y registro de usuarios.
 */
@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Maneja la solicitud de inicio de sesión de un usuario.
     *
     * @param authRequestDto Objeto {@link AuthRequestDto} que contiene el nombre de usuario y la contraseña.
     * @return Una respuesta {@link ResponseEntity} con un objeto {@link AuthResponseDto} que contiene el token JWT,
     *         el estado de la autenticación y un mensaje. Si el inicio de sesión es exitoso, devuelve un código de estado HTTP 200 (OK).
     *         Si falla, devuelve un código de estado HTTP 401 (UNAUTHORIZED) con un mensaje de error.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        try {
            var jwtToken = authService.login(authRequestDto.userName(), authRequestDto.password());

            var authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.LOGIN_SUCCESS, "Login successful");

            return ResponseEntity.status(HttpStatus.OK).body(authResponseDto);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            AuthStatus status = AuthStatus.LOGIN_FAILED;

            if (e.getMessage().contains("Bad credentials")) {
                errorMessage = "The username or password is incorrect";
            } else if (e.getMessage().contains("User not found")) {
                errorMessage = "User not found";
            }
            var authResponseDto = new AuthResponseDto(null, status, errorMessage);

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(authResponseDto);
        }
    }

    /**
     * Maneja la solicitud de registro de un nuevo usuario.
     *
     * @param authRequestDto Objeto {@link AuthRequestDto} que contiene el nombre, nombre de usuario, contraseña y correo electrónico del nuevo usuario.
     * @return Una respuesta {@link ResponseEntity} con un objeto {@link AuthResponseDto} que contiene el token JWT,
     *         el estado del registro y un mensaje. Si el registro es exitoso, devuelve un código de estado HTTP 200 (OK).
     *         Si falla, devuelve un código de estado HTTP 409 (CONFLICT) con un mensaje de error.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody AuthRequestDto authRequestDto) {
        try {
            var jwtToken = authService.signUp(authRequestDto.name(), authRequestDto.userName(), authRequestDto.password(), authRequestDto.mail());
            var authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.USER_CREATED_SUCCESSFULLY, "User registered successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authResponseDto);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            AuthStatus status = AuthStatus.USER_NOT_CREATED;

            if (e.getMessage().contains("Username already exist")) {
                errorMessage = "The username is already in use";
            } else if (e.getMessage().contains("Email already exist")) {
                errorMessage = "Email is already in use";
            }

            var authResponseDto = new AuthResponseDto(null, status, errorMessage);

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(authResponseDto);
        }
    }
}
