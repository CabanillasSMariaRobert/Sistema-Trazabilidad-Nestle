package com.nestle.trazabilidad.model;

import lombok.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Lotes {

    private Integer idLote;
    private Integer idProducto;
    private String codigoLote;
    private LocalDate fechaFabricacion;
    private LocalDate fechaVencimiento;
    private String planta;
    private String estado;
}