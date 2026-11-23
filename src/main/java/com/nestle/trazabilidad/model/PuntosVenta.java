package com.nestle.trazabilidad.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PuntosVenta {

    private Integer idPunto;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String tipo;
    private String estado;
}