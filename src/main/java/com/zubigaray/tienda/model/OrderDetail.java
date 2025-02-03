package com.zubigaray.tienda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Representa una entidad de detalle de pedido (OrderDetail) en el sistema.
 * Esta clase está mapeada a la tabla "OrderDetails" en la base de datos y contiene información sobre los productos
 * asociados a un pedido, incluyendo la cantidad y el subtotal de cada producto.
 */
@Entity
@Table(name = "OrderDetails")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetail {

    /**
     * Identificador único del detalle de pedido. Este campo se genera automáticamente mediante una estrategia de identidad en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pedido al que pertenece este detalle. Este campo representa una relación muchos-a-uno con la entidad {@link Order}.
     * La columna "order_id" en la base de datos no puede ser nula.
     */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Producto asociado a este detalle de pedido. Este campo representa una relación muchos-a-uno con la entidad {@link Product}.
     * La columna "product_id" en la base de datos no puede ser nula.
     */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Cantidad del producto en este detalle de pedido. Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Subtotal del detalle de pedido, calculado como el precio del producto multiplicado por la cantidad.
     * Este campo no puede ser nulo y se define con una precisión de 10 dígitos y 2 decimales.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotal;
}
