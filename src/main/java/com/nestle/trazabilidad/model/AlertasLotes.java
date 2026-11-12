package com.nestle.trazabilidad.model;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AlertasLotes {
    private Integer idAlertaLote;
    private Integer idAlerta;
    private Integer idLote;
    private Integer idUsuario;
    private LocalDateTime fechaAsociacion;
}
