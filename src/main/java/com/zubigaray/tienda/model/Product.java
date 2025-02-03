package com.zubigaray.tienda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Representa una entidad de producto (Product) en el sistema.
 * Esta clase está mapeada a la tabla "Products" en la base de datos y contiene información sobre un producto,
 * incluyendo su nombre, descripción, precio y stock disponible.
 */
@Entity
@Table(name = "Products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    /**
     * Identificador único del producto. Este campo se genera automáticamente mediante una estrategia de identidad en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del producto. Este campo no puede ser nulo y tiene una longitud máxima de 100 caracteres.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Descripción detallada del producto. Este campo no puede ser nulo y se almacena como un objeto grande (LOB) en la base de datos.
     */
    @Lob
    @Column(nullable = false)
    private String description;

    /**
     * Precio del producto. Este campo no puede ser nulo y se define con una precisión de 10 dígitos y 2 decimales.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Cantidad de stock disponible del producto. Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private Integer stock;
}
