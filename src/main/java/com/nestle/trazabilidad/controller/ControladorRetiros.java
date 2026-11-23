package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.service.AlertaLoteService;
import com.nestle.trazabilidad.service.DetalleRetiroService;
import com.nestle.trazabilidad.service.DistribucionService;
import com.nestle.trazabilidad.service.RetiroService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.service.TrazabilidadService;
import com.nestle.trazabilidad.view.paneles.PanelRetiros;

/** Controller de retiros y recuperaciones (CU-07 y CU-09). */
public class ControladorRetiros extends ControladorBase {

    private final PanelRetiros vista;
    private final RetiroService retiroService = ServiceFactory.retiro();
    private final DetalleRetiroService detalleService = ServiceFactory.detalleRetiro();
    private final AlertaLoteService alertaLoteService = ServiceFactory.alertaLote();
    private final DistribucionService distribucionService = ServiceFactory.distribucion();
    private final TrazabilidadService trazabilidadService = ServiceFactory.trazabilidad();

    public ControladorRetiros(PanelRetiros vista) {
        this.vista = vista;
        vista.getBtnActualizar().addActionListener(e -> cargarRetiros());
        vista.getBtnIniciar().addActionListener(e -> iniciarRetiro());
        vista.getBtnCerrar().addActionListener(e -> cerrarRetiro());
        vista.getBtnRecuperar().addActionListener(e -> registrarRecuperacion());
    }

    public void cargarRetiros() {
        try {
            vista.setRetiros(retiroService.listarTodos());
            vista.setAsociaciones(alertaLoteService.listarTodos());
            vista.setDistribuciones(distribucionService.listarTodos());
            vista.setDetalles(detalleService.listarTodos());
        } catch (Exception e) {
            mostrarError(vista, "Error al cargar retiros", e);
        }
    }

    private void iniciarRetiro() {
        Integer idAlertaLote = vista.getIdAlertaLoteSeleccionado();
        Integer idUsuario = enteroDe(vista.getTxtIdUsuario().getText());
        if (idAlertaLote == null || idUsuario == null) {
            mostrarInformacion(vista, "Seleccione una asociación alerta-lote e ingrese el ID del usuario.");
            return;
        }
        try {
            Integer idRetiro = trazabilidadService.iniciarRetiro(
                    idAlertaLote, idUsuario, vista.getTxtObservaciones().getText().trim());
            mostrarInformacion(vista, "Retiro iniciado con ID " + idRetiro);
            cargarRetiros();
        } catch (Exception e) {
            mostrarError(vista, "Error al iniciar retiro", e);
        }
    }

    private void cerrarRetiro() {
        Integer idRetiro = vista.getRetiroDeTabla() != null
                ? vista.getRetiroDeTabla().getIdRetiro()
                : vista.getIdRetiroSeleccionado();
        Integer idUsuario = enteroDe(vista.getTxtIdUsuario().getText());
        if (idRetiro == null || idUsuario == null) {
            mostrarInformacion(vista, "Seleccione un retiro e ingrese el ID del usuario.");
            return;
        }
        try {
            if (trazabilidadService.cerrarRetiro(idRetiro, idUsuario)) {
                mostrarInformacion(vista, "Retiro cerrado correctamente.");
                cargarRetiros();
            } else {
                mostrarInformacion(vista, "No se pudo cerrar el retiro.");
            }
        } catch (Exception e) {
            mostrarError(vista, "Error al cerrar retiro", e);
        }
    }

    private void registrarRecuperacion() {
        Integer idRetiro = vista.getIdRetiroSeleccionado();
        Integer idDistribucion = vista.getIdDistribucionSeleccionada();
        Integer idUsuario = enteroDe(vista.getTxtIdUsuarioRec().getText());
        Integer cantidad = enteroDe(vista.getTxtCantidad().getText());
        if (idRetiro == null || idDistribucion == null || idUsuario == null || cantidad == null) {
            mostrarInformacion(vista, "Seleccione retiro y distribución; ingrese usuario y cantidad.");
            return;
        }
        if (cantidad <= 0) {
            mostrarInformacion(vista, "La cantidad recuperada debe ser mayor a cero.");
            return;
        }
        try {
            Integer idDetalle = trazabilidadService.registrarRecuperacion(
                    idRetiro, idDistribucion, idUsuario, cantidad,
                    vista.getTxtObservacionesRec().getText().trim());
            mostrarInformacion(vista, "Recuperación registrada con ID " + idDetalle);
            cargarRetiros();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar recuperación", e);
        }
    }
}
