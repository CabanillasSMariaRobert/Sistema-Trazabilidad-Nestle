package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "permisos")
public class PermisosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Integer idPermiso;
    @Column(name = "codigo", length = 80, nullable = false)
    private String codigo;
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    @Column (name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "permiso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RolesPermisosEntity> roles = new ArrayList<>();

}


