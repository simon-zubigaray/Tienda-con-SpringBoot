package com.zubigaray.tienda.repo;

import com.zubigaray.tienda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad {@link User}. Proporciona métodos para acceder y manipular datos de usuarios en la base de datos.
 * Esta interfaz extiende {@link JpaRepository}, lo que permite utilizar métodos CRUD estándar y personalizados.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    /**
     * Verifica si existe un usuario con el nombre de usuario especificado.
     *
     * @param userName El nombre de usuario a verificar.
     * @return {@code true} si existe un usuario con el nombre de usuario proporcionado, {@code false} en caso contrario.
     */
    boolean existsByUserName(String userName);

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param userName El nombre de usuario del usuario a buscar.
     * @return Un {@link Optional} que contiene el usuario si se encuentra, o vacío si no existe.
     */
    Optional<User> findByUserName(String userName);

    /**
     * Verifica si existe un usuario con el correo electrónico especificado.
     *
     * @param mail El correo electrónico a verificar.
     * @return {@code true} si existe un usuario con el correo electrónico proporcionado, {@code false} en caso contrario.
     */
    boolean existsByMail(String mail);
}
