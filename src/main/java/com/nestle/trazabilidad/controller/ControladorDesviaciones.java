package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.entity.ControlesCalidadEntity;
import com.nestle.trazabilidad.entity.DesviacionesEntity;
import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;
import com.nestle.trazabilidad.service.DesviacionService;
import com.nestle.trazabilidad.service.LoteService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.view.VistaUtil;
import com.nestle.trazabilidad.view.paneles.PanelDesviaciones;

import java.util.ArrayList;
import java.util.List;

/** Controller del panel de desviaciones (CU-05). */
public class ControladorDesviaciones extends ControladorBase {

    private final PanelDesviaciones vista;
    private final DesviacionService service = ServiceFactory.desviacion();
    private final LoteService loteService = ServiceFactory.lote();

    public ControladorDesviaciones(PanelDesviaciones vista) {
        this.vista = vista;
        vista.getBtnRegistrar().addActionListener(e -> registrar());
        vista.getBtnActualizarLista().addActionListener(e -> cargarDesviaciones());
        vista.getBtnCambiarEstado().addActionListener(e -> cambiarEstado());
    }

    public void cargarDesviaciones() {
        try {
            vista.setDesviaciones(service.listarTodos());
            List<String> etiquetas = new ArrayList<>();
            for (LotesEntity l : loteService.listarTodos()) {
                etiquetas.add(l.getIdLote() + " - " + l.getCodigoLote());
            }
            vista.setLotes(etiquetas);
        } catch (Exception e) {
            mostrarError(vista, "Error al cargar desviaciones", e);
        }
    }

    private void registrar() {
        Integer idLote = VistaUtil.idDeCombo(vista.getCbLote().getSelectedItem());
        Integer idUsuario = enteroDe(vista.getTxtIdUsuario().getText());
        Integer idControl = enteroDe(vista.getTxtIdControl().getText());
        if (idLote == null || idUsuario == null) {
            mostrarInformacion(vista, "Seleccione un lote e ingrese el ID del usuario.");
            return;
        }
        try {
            LotesEntity lote = new LotesEntity();
            lote.setIdLote(idLote);
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setIdUsuario(idUsuario);

            DesviacionesEntity d = new DesviacionesEntity();
            d.setLote(lote);
            d.setUsuario(usuario);
            if (idControl != null) {
                ControlesCalidadEntity control = new ControlesCalidadEntity();
                control.setIdControl(idControl);
                d.setControl(control);
            }
            d.setFechaDeteccion(fechaHoraDe(vista.getTxtFechaDeteccion().getText()));
            d.setFuente(vista.getTxtFuente().getText().trim());
            d.setDescripcion(vista.getTxtDescripcion().getText().trim());
            d.setSeveridad((String) vista.getCbSeveridad().getSelectedItem());
            d.setEstado((String) vista.getCbEstado().getSelectedItem());

            Integer id = service.insertar(d);
            mostrarInformacion(vista, "Desviación registrada con ID " + id);
            cargarDesviaciones();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar desviación", e);
        }
    }

    private void cambiarEstado() {
        DesviacionesEntity d = vista.getDesviacionSeleccionada();
        if (d == null) {
            mostrarInformacion(vista, "Seleccione una desviación.");
            return;
        }
        String estado = (String) vista.getCbEstadoAccion().getSelectedItem();
        try {
            if (service.actualizarEstado(d.getIdDesviacion(), estado)) {
                mostrarInformacion(vista, "Estado actualizado a " + estado);
                cargarDesviaciones();
            } else {
                mostrarInformacion(vista, "No se pudo actualizar el estado.");
            }
        } catch (Exception e) {
            mostrarError(vista, "Error al cambiar estado", e);
        }
    }
}
