package com.nestle.trazabilidad.model;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BitacorasOperaciones {
    private Integer idOperacion;
    private Integer idUsuario;
    private LocalDateTime fechaHora;
    private String accion;
    private String entidadAfectada;
    private String identificadorRegistro;
    private String valoresAnteriores;
    private String valoresNuevos;
}
