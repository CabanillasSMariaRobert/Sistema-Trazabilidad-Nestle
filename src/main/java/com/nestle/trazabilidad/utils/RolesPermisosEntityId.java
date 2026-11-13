package com.nestle.trazabilidad.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class RolesPermisosEntityId implements Serializable {
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "id_permiso")
    private Integer idPermiso;

}
