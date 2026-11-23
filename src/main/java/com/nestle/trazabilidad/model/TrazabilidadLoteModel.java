package com.nestle.trazabilidad.model;

import com.nestle.trazabilidad.entity.*;
import lombok.*;

import java.util.List;

/** Historial completo de un lote: producto, controles, desviaciones, alertas y distribuciones. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrazabilidadLoteModel {
    private LotesEntity lote;
    private ProductosEntity producto;
    private List<ControlesCalidadEntity> controles;
    private List<DesviacionesEntity> desviaciones;
    private List<AlertasSanitariasEntity> alertas;
    private List<DistribucionesEntity> distribuciones;
}
