package com.nestle.trazabilidad.model;

import com.nestle.trazabilidad.enums.EstadosAlertas;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.nestle.trazabilidad.enums.EstadosAlertas.ACTIVA;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AlertasSanitarias {
    private Integer idAlerta;
    private Integer idUsuario;
    private String nroComunicado;
    private LocalDate fechaEmision;
    private LocalDateTime fechaRegistro;
    private String origen;
    private String descripcion;
    @Builder.Default
    private EstadosAlertas estado = ACTIVA;
}
