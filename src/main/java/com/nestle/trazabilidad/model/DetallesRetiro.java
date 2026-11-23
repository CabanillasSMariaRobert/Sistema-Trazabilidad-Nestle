package com.nestle.trazabilidad.model;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DetallesRetiro {

    private Integer idDetalleRetiro;
    private Integer idRetiro;
    private Integer idDistribucion;
    private Integer idUsuario;
    private LocalDateTime fechaRecuperacion;
    private Integer cantidadRecuperada;
    private String observaciones;
}