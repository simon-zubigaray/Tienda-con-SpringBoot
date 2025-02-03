package com.zubigaray.tienda.service;

import com.zubigaray.tienda.model.User;
import com.zubigaray.tienda.repo.UserRepo;
import com.zubigaray.tienda.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementación del servicio de autenticación ({@link AuthService}).
 * Esta clase proporciona la lógica para el inicio de sesión, registro y verificación de tokens de autenticación.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    /**
     * Autentica a un usuario con las credenciales proporcionadas y genera un token de autenticación.
     *
     * @param userName El nombre de usuario del usuario que intenta iniciar sesión.
     * @param password La contraseña del usuario.
     * @return Un token de autenticación si las credenciales son válidas.
     * @throws RuntimeException Si la autenticación falla.
     */
    @Override
    public String login(String userName, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(userName, password);
        var authentication = this.authenticationManager.authenticate(authToken);
        return JwtUtils.generateToken(((UserDetails) (authentication.getPrincipal())).getUsername());
    }

    /**
     * Registra un nuevo usuario en el sistema con la información proporcionada y genera un token de autenticación.
     *
     * @param name     El nombre completo del usuario.
     * @param userName El nombre de usuario único para el nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     * @param mail     El correo electrónico del nuevo usuario.
     * @return Un token de autenticación si el registro es exitoso.
     * @throws RuntimeException Si el nombre de usuario o el correo electrónico ya existen en el sistema.
     */
    @Override
    public String signUp(String name, String userName, String password, String mail) {
        if (userRepo.existsByUserName(userName)) {
            throw new RuntimeException("Existing UserName");
        }
        if (userRepo.existsByMail(mail)) {
            throw new RuntimeException("Existing Mail");
        }

        User user = new User();
        user.setName(name);
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setMail(mail);
        user.setRegisterDate(LocalDateTime.now());

        userRepo.save(user);
        System.out.println("Saved User: " + user.getName());

        return JwtUtils.generateToken(userName);
    }

    /**
     * Verifica la validez de un token de autenticación y devuelve el nombre de usuario asociado.
     *
     * @param token El token de autenticación a verificar.
     * @return El nombre de usuario asociado al token si es válido.
     * @throws RuntimeException Si el token es inválido.
     */
    @Override
    public String verifyToken(String token) {
        var userNameOptional = JwtUtils.getUserNameFromToken(token);

        if (userNameOptional.isPresent()) {
            return userNameOptional.get();
        }

        throw new RuntimeException("Invalid Token");
    }
}