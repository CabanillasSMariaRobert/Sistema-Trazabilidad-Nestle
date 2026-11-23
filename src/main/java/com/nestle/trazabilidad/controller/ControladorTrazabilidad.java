package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.service.LoteService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.service.TrazabilidadService;
import com.nestle.trazabilidad.view.paneles.PanelTrazabilidad;

import java.util.ArrayList;
import java.util.List;

/** Controller de la consulta de trazabilidad (CU-03). */
public class ControladorTrazabilidad extends ControladorBase {

    private final PanelTrazabilidad vista;
    private final LoteService loteService = ServiceFactory.lote();
    private final TrazabilidadService trazabilidadService = ServiceFactory.trazabilidad();

    public ControladorTrazabilidad(PanelTrazabilidad vista) {
        this.vista = vista;
        vista.getBtnBuscar().addActionListener(e -> buscar());
    }

    public void cargarLotes() {
        try {
            List<String> etiquetas = new ArrayList<>();
            for (LotesEntity l : loteService.listarTodos()) {
                etiquetas.add(l.getIdLote() + " - " + l.getCodigoLote());
            }
            vista.setLotes(etiquetas);
        } catch (Exception e) {
            mostrarError(vista, "Error al cargar lotes", e);
        }
    }

    private void buscar() {
        Integer idLote = vista.getIdLoteSeleccionado();
        if (idLote == null) {
            mostrarInformacion(vista, "Seleccione un lote.");
            return;
        }
        try {
            vista.setTrazabilidad(trazabilidadService.consultarTrazabilidad(idLote));
        } catch (Exception e) {
            mostrarError(vista, "Error al consultar trazabilidad", e);
        }
    }
}
