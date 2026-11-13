package com.nestle.trazabilidad.model;

import com.nestle.trazabilidad.enums.Estados;
import lombok.*;

import static com.nestle.trazabilidad.enums.Estados.ACTIVO;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Roles {
    private Integer rol;
    private String nombre;
    private String descripcion;
    @Builder.Default
    private Estados estado = ACTIVO;
}
