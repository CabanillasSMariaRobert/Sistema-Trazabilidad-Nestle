package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.entity.ControlesCalidadEntity;
import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;
import com.nestle.trazabilidad.service.ControlCalidadService;
import com.nestle.trazabilidad.service.LoteService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.view.VistaUtil;
import com.nestle.trazabilidad.view.paneles.PanelControlesCalidad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Controller del panel de controles de calidad (CU-04). */
public class ControladorControlesCalidad extends ControladorBase {

    private final PanelControlesCalidad vista;
    private final ControlCalidadService service = ServiceFactory.controlCalidad();
    private final LoteService loteService = ServiceFactory.lote();

    public ControladorControlesCalidad(PanelControlesCalidad vista) {
        this.vista = vista;
        vista.getBtnRegistrar().addActionListener(e -> registrar());
        vista.getBtnActualizarLista().addActionListener(e -> cargarControles());
    }

    public void cargarControles() {
        try {
            vista.setControles(service.listarTodos());
            List<String> etiquetas = new ArrayList<>();
            for (LotesEntity l : loteService.listarTodos()) {
                etiquetas.add(l.getIdLote() + " - " + l.getCodigoLote());
            }
            vista.setLotes(etiquetas);
        } catch (Exception e) {
            mostrarError(vista, "Error al cargar controles", e);
        }
    }

    private void registrar() {
        Integer idLote = VistaUtil.idDeCombo(vista.getCbLote().getSelectedItem());
        Integer idUsuario = enteroDe(vista.getTxtIdUsuario().getText());
        if (idLote == null || idUsuario == null) {
            mostrarInformacion(vista, "Seleccione un lote e ingrese el ID del usuario.");
            return;
        }
        try {
            LotesEntity lote = new LotesEntity();
            lote.setIdLote(idLote);
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setIdUsuario(idUsuario);

            ControlesCalidadEntity control = new ControlesCalidadEntity();
            control.setLote(lote);
            control.setUsuario(usuario);
            control.setFechaControl(fechaHoraDe(vista.getTxtFechaControl().getText()));
            control.setParametro(vista.getTxtParametro().getText().trim());
            control.setValorObtenido(vista.getTxtValorObtenido().getText().trim());
            control.setResultado((String) vista.getCbResultado().getSelectedItem());
            control.setObservaciones(vista.getTxtObservaciones().getText().trim());

            Integer id = service.insertar(control);
            mostrarInformacion(vista, "Control registrado con ID " + id);
            cargarControles();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar control", e);
        }
    }
}
