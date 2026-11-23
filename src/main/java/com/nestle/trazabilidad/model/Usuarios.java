package com.nestle.trazabilidad.model;

import lombok.*;

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
    private boolean estado;
}
