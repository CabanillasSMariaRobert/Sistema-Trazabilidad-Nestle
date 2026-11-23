package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RolesPermisos")
@IdClass(RolPermisoId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolPermisoEntity {

    @Id
    @Column(name = "id_rol")
    private Integer idRol;

    @Id
    @Column(name = "id_permiso")
    private Integer idPermiso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", insertable = false, updatable = false)
    private RolEntity rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permiso", insertable = false, updatable = false)
    private PermisoEntity permiso;
}