package com.nestle.trazabilidad.model;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AtencionesReclamos {

    private Integer idAtencion;
    private Integer idReclamo;
    private Integer idUsuario;
    private LocalDateTime fechaAtencion;
    private String accionRealizada;
    private String respuesta;
}