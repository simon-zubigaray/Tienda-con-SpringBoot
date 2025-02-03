package com.zubigaray.tienda.service;

import com.zubigaray.tienda.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Implementación personalizada de {@link UserDetailsService} para cargar los detalles de un usuario
 * desde la base de datos utilizando el repositorio de usuarios ({@link UserRepo}).
 * Esta clase es utilizada por Spring Security para autenticar y autorizar usuarios.
 */
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    /**
     * Carga los detalles de un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario que se desea cargar.
     * @return Un objeto {@link UserDetails} que contiene la información del usuario.
     * @throws UsernameNotFoundException Si no se encuentra un usuario con el nombre de usuario proporcionado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        return User
                .builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .build();
    }
}