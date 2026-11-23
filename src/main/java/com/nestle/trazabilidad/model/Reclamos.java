package com.nestle.trazabilidad.model;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Reclamos {

    private Integer idReclamo;
    private Integer idProducto;
    private Integer idLote;
    private Integer idUsuarioRegistro;
    private LocalDateTime fechaRegistro;
    private String nombreConsumidor;
    private String contacto;
    private String motivo;
    private String descripcion;
    private String estado;
}