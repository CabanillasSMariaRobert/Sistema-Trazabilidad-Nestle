package com.nestle.trazabilidad.entity;

import jakarta.persistence.Column;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RolPermisoId implements Serializable {

    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "id_permiso")
    private Integer idPermiso;
}