package com.nestle.trazabilidad.model;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Desviaciones {

    private Integer idDesviacion;
    private Integer idLote;
    private Integer idControl;
    private Integer idUsuario;
    private LocalDateTime fechaDeteccion;
    private String fuente;
    private String descripcion;
    private String severidad;
    private String estado;
}