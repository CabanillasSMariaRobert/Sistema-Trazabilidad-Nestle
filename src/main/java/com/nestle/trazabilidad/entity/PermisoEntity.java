package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Permisos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Integer idPermiso;

    @Column(name = "codigo", length = 80, unique = true)
    private String codigo;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 200)
    private String descripcion;
}