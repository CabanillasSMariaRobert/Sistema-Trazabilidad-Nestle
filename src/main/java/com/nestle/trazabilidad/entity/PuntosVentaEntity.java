package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PuntosVenta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PuntosVentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_punto")
    private Integer idPunto;

    @Column(name = "nombre", length = 120)
    private String nombre;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "ciudad", length = 80)
    private String ciudad;

    @Column(name = "tipo", length = 40)
    private String tipo;

    @Column(name = "estado", length = 20)
    private String estado;
}