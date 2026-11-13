package com.nestle.trazabilidad.entity;

import com.mysql.cj.x.protobuf.MysqlxCursor;
import com.nestle.trazabilidad.utils.RolesPermisosEntityId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RolesPermisosEntity {
    @EmbeddedId
    @Builder.Default
    private RolesPermisosEntityId id = new RolesPermisosEntityId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId ("idRol")
    @JoinColumn (name = "id_rol", nullable = false)
    private RolesEntity rol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId ("idPermiso")
    @JoinColumn (name = "id_permiso", nullable = false)
    private PermisosEntity permiso;
}

