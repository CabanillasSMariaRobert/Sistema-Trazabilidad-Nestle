package com.nestle.trazabilidad.model.filtros;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FiltroBitacorasModel {

    private Integer idUsuario;
    private LocalDateTime desde;
    private LocalDateTime hasta;
    private String accion;
    private String entidadAfectada;
    private String identificadorRegistro;
}