package com.nestle.trazabilidad.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Permisos {
    private Integer idPermiso;
    private String codigo;
    private String nombre;
    private String descripcion;
}
