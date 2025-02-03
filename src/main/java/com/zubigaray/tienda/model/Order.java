package com.zubigaray.tienda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa una entidad de pedido (Order) en el sistema.
 * Esta clase está mapeada a la tabla "Orders" en la base de datos y contiene información sobre un pedido realizado por un usuario.
 */
@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    /**
     * Identificador único del pedido. Este campo se genera automáticamente mediante una estrategia de identidad en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario asociado al pedido. Este campo representa una relación muchos-a-uno con la entidad {@link User}.
     * La relación está configurada para realizar operaciones en cascada (CascadeType.ALL) y no puede ser nula.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Fecha y hora en que se realizó el pedido. Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private LocalDateTime date;

    /**
     * Precio total del pedido. Este campo no puede ser nulo y se define con una precisión de 10 dígitos y 2 decimales.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;
}
