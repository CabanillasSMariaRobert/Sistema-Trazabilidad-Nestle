package com.nestle.trazabilidad.model;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Distribuciones {

    private Integer idDistribucion;
    private Integer idLote;
    private Integer idPunto;
    private Integer idUsuario;
    private LocalDateTime fechaEnvio;
    private Integer cantidadEnviada;
    private String estado;
}