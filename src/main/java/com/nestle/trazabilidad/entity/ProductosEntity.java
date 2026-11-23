package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "codigo", length = 30)
    private String codigo;

    @Column(name = "nombre", length = 120)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "categoria", length = 60)
    private String categoria;

    @Column(name = "estado", length = 20)
    private String estado;
}