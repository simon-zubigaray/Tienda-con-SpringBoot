package com.zubigaray.tienda.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Representa una entidad de usuario (User) en el sistema.
 * Esta clase está mapeada a la tabla "Users" en la base de datos y contiene información sobre un usuario,
 * incluyendo su nombre, nombre de usuario, contraseña, correo electrónico y fecha de registro.
 */
@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * Identificador único del usuario. Este campo se genera automáticamente mediante una estrategia de identidad en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del usuario. Este campo puede ser nulo.
     */
    @Column(nullable = true)
    private String name;

    /**
     * Nombre de usuario único para identificar al usuario en el sistema. Este campo no puede ser nulo y debe ser único.
     */
    @Column(nullable = false, unique = true)
    private String userName;

    /**
     * Correo electrónico del usuario. Este campo no puede ser nulo y debe ser único.
     */
    @Column(nullable = false, unique = true)
    private String mail;

    /**
     * Contraseña del usuario para autenticación. Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Fecha y hora en que el usuario se registró en el sistema. Este campo no puede ser nulo y no se puede actualizar una vez establecido.
     */
    @Column(name = "register_date", nullable = false, updatable = false)
    private LocalDateTime registerDate;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }
}
