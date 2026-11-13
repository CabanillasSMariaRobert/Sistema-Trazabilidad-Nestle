package com.nestle.trazabilidad.model;

import com.nestle.trazabilidad.enums.Estados;
import lombok.*;

import static com.nestle.trazabilidad.enums.Estados.ACTIVO;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Usuarios {
    private Integer idUsuario;
    private String nombres;
    private String apellidos;
    private String userName;
    private String passwordHash;
    @Builder.Default
    private Estados estado = ACTIVO;
}
