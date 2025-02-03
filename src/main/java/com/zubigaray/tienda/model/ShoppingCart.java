package com.zubigaray.tienda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa una entidad de carrito de compras (ShoppingCart) en el sistema.
 * Esta clase está mapeada a la tabla "ShoppingCart" en la base de datos y contiene información sobre los productos
 * que un usuario ha agregado a su carrito de compras, incluyendo la cantidad de cada producto.
 */
@Entity
@Table(name = "ShoppingCart")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCart {

    /**
     * Identificador único del carrito de compras. Este campo se genera automáticamente mediante una estrategia de identidad en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario asociado al carrito de compras. Este campo representa una relación muchos-a-uno con la entidad {@link User}.
     * La columna "user_id" en la base de datos no puede ser nula.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Producto agregado al carrito de compras. Este campo representa una relación muchos-a-uno con la entidad {@link Product}.
     * La columna "product_id" en la base de datos no puede ser nula.
     */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Cantidad del producto en el carrito de compras. Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private Integer quantity;
}
