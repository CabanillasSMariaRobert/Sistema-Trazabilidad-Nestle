package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.entity.BitacoresOperacionesEntity;
import com.nestle.trazabilidad.model.filtros.FiltroBitacorasModel;
import com.nestle.trazabilidad.service.BitacoraOperacionService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.view.paneles.PanelBitacoras;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/** Controller del panel de bitácoras de operaciones. */
public class ControladorBitacoras extends ControladorBase {

    private static final int LIMITE = 200;

    private final PanelBitacoras vista;
    private final BitacoraOperacionService bitacoraService = ServiceFactory.bitacoraOperacion();

    public ControladorBitacoras(PanelBitacoras vista) {
        this.vista = vista;

        vista.getBtnBuscar().addActionListener(e -> cargarBitacoras());
        vista.getBtnLimpiar().addActionListener(e -> {
            vista.limpiarFiltros();
            cargarBitacoras();
        });
    }

    public void cargarBitacoras() {
        try {
            FiltroBitacorasModel filtro = new FiltroBitacorasModel();

            Integer idUsuario = parseInt(vista.getTxtIdUsuario().getText());
            filtro.setIdUsuario(idUsuario);
            filtro.setDesde(parseFecha(vista.getTxtDesde().getText()));
            filtro.setHasta(parseFechaHasta(vista.getTxtHasta().getText()));
            filtro.setAccion(vista.getTxtAccion().getText().trim());
            filtro.setEntidadAfectada(vista.getTxtEntidad().getText().trim());
            filtro.setIdentificadorRegistro(vista.getTxtIdentificador().getText().trim());

            List<BitacoresOperacionesEntity> operaciones =
                    bitacoraService.listarConFiltros(filtro, LIMITE, 0);
            vista.setOperaciones(operaciones);
        } catch (SQLException e) {
            mostrarError(vista, "Error al cargo la bitácora", e);
        }
    }

    private Integer parseInt(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            return Integer.valueOf(valor.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private LocalDateTime parseFecha(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(valor.trim());
        } catch (Exception e) {
            try {
                return java.time.LocalDate.parse(valor.trim()).atStartOfDay();
            } catch (Exception e2) {
                return null;
            }
        }
    }

    private LocalDateTime parseFechaHasta(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(valor.trim());
        } catch (Exception e) {
            try {
                return java.time.LocalDate.parse(valor.trim()).atTime(23, 59, 59);
            } catch (Exception e2) {
                return null;
            }
        }
    }
}
