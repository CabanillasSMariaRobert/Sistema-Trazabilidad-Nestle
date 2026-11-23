package com.nestle.trazabilidad.model;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Retiros {

    private Integer idRetiro;
    private Integer idAlertaLote;
    private Integer idUsuario;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;
    private String estado;
    private String observaciones;
}