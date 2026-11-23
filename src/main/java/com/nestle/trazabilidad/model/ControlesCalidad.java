package com.nestle.trazabilidad.model;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ControlesCalidad {

    private Integer idControl;
    private Integer idLote;
    private Integer idUsuario;
    private LocalDateTime fechaControl;
    private String parametro;
    private String valorObtenido;
    private String resultado;
    private String observaciones;
}