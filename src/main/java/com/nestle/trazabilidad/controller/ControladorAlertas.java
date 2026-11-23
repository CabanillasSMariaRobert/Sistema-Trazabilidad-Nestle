package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.entity.AlertasSanitariasEntity;
import com.nestle.trazabilidad.enums.EstadosAlertas;
import com.nestle.trazabilidad.service.AlertaSanitariaService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.service.TrazabilidadService;
import com.nestle.trazabilidad.view.paneles.PanelAlertas;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Controller del panel de alertas sanitarias. */
public class ControladorAlertas extends ControladorBase {

    private final PanelAlertas vista;
    private final AlertaSanitariaService alertaService = ServiceFactory.alertaSanitaria();
    private final TrazabilidadService trazabilidadService = ServiceFactory.trazabilidad();

    public ControladorAlertas(PanelAlertas vista) {
        this.vista = vista;

        vista.getBtnRegistrar().addActionListener(e -> registrar());
        vista.getBtnActualizar().addActionListener(e -> cargarAlertas());
        vista.getBtnAsociarLote().addActionListener(e -> asociarLote());
        vista.getBtnCerrar().addActionListener(e -> cerrarAlerta());
    }

    public void cargarAlertas() {
        try {
            List<AlertasSanitariasEntity> lista = new ArrayList<>();
            lista.addAll(alertaService.listarPorEstado(EstadosAlertas.ACTIVA));
            lista.addAll(alertaService.listarPorEstado(EstadosAlertas.CERRADA));
            vista.setAlertas(lista);
        } catch (SQLException e) {
            mostrarError(vista, "Error al cargar alertas", e);
        }
    }

    private void registrar() {
        try {
            LocalDate fechaEmision = LocalDate.parse(vista.getTxtFechaEmision().getText().trim());
            Integer idUsuario = Integer.valueOf(vista.getTxtIdUsuario().getText().trim());

            AlertasSanitariasEntity alerta = new AlertasSanitariasEntity();
            alerta.setNroComunicado(vista.getTxtNroComunicado().getText().trim());
            alerta.setOrigen(vista.getTxtOrigen().getText().trim());
            alerta.setDescripcion(vista.getTxtDescripcion().getText().trim());
            alerta.setFechaEmision(fechaEmision);
            alerta.setFechaRegistro(LocalDateTime.now());
            alerta.setEstado(EstadosAlertas.ACTIVA);
            com.nestle.trazabilidad.entity.UsuarioEntity usuario = new com.nestle.trazabilidad.entity.UsuarioEntity();
            usuario.setIdUsuario(idUsuario);
            alerta.setUsuario(usuario);

            Integer id = alertaService.insertar(alerta);
            mostrarInformacion(vista, "Alerta registrada con ID " + id);
            vista.limpiarFormulario();
            cargarAlertas();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar alerta", e);
        }
    }

    private void asociarLote() {
        AlertasSanitariasEntity alerta = vista.getAlertaSeleccionada();
        if (alerta == null) {
            mostrarInformacion(vista, "Seleccione una alerta en la tabla.");
            return;
        }

        Integer idLote = pedirEntero(vista, "ID del lote a asociar:", "Asociar lote");
        Integer idUsuario = pedirEntero(vista, "ID del usuario que realiza la acción:", "Asociar lote");

        if (idLote == null || idUsuario == null) {
            return;
        }

        try {
            Integer idAsociacion = trazabilidadService.asociarLoteAAlerta(
                    alerta.getIdAlerta(), idLote, idUsuario);
            mostrarInformacion(vista, "Lote asociado. ID de asociación: " + idAsociacion);
        } catch (SQLException e) {
            mostrarError(vista, "Error al asociar lote", e);
        }
    }

    private void cerrarAlerta() {
        AlertasSanitariasEntity alerta = vista.getAlertaSeleccionada();
        if (alerta == null) {
            mostrarInformacion(vista, "Seleccione una alerta en la tabla.");
            return;
        }

        try {
            boolean ok = alertaService.actualizarEstado(alerta.getIdAlerta(), EstadosAlertas.CERRADA);
            if (ok) {
                mostrarInformacion(vista, "Alerta cerrada correctamente.");
                cargarAlertas();
            } else {
                mostrarInformacion(vista, "No se pudo cerrar la alerta.");
            }
        } catch (SQLException e) {
            mostrarError(vista, "Error al cerrar alerta", e);
        }
    }
}
