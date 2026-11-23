package com.nestle.trazabilidad.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Productos {

    private Integer idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String estado;
}