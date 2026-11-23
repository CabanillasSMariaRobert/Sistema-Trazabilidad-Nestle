package com.nestle.trazabilidad.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Roles {

    private Integer idRol;
    private String nombre;
    private String descripcion;
    private String estado;
}