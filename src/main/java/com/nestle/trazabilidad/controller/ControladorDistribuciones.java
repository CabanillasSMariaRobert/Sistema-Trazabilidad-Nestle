package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.entity.DistribucionesEntity;
import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.PuntosVentaEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;
import com.nestle.trazabilidad.service.DistribucionService;
import com.nestle.trazabilidad.service.LoteService;
import com.nestle.trazabilidad.service.PuntoVentaService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.view.VistaUtil;
import com.nestle.trazabilidad.view.paneles.PanelDistribuciones;

import java.util.ArrayList;
import java.util.List;

/** Controller del panel de distribuciones (CU-08). */
public class ControladorDistribuciones extends ControladorBase {

    private final PanelDistribuciones vista;
    private final DistribucionService service = ServiceFactory.distribucion();
    private final LoteService loteService = ServiceFactory.lote();
    private final PuntoVentaService puntoService = ServiceFactory.puntoVenta();

    public ControladorDistribuciones(PanelDistribuciones vista) {
        this.vista = vista;
        vista.getBtnRegistrar().addActionListener(e -> registrar());
        vista.getBtnActualizarLista().addActionListener(e -> cargarDistribuciones());
    }

    public void cargarDistribuciones() {
        try {
            vista.setDistribuciones(service.listarTodos());

            List<String> lotes = new ArrayList<>();
            for (LotesEntity l : loteService.listarTodos()) {
                lotes.add(l.getIdLote() + " - " + l.getCodigoLote());
            }
            vista.setLotes(lotes);

            List<String> puntos = new ArrayList<>();
            for (PuntosVentaEntity p : puntoService.listarTodos()) {
                puntos.add(p.getIdPunto() + " - " + p.getNombre());
            }
            vista.setPuntos(puntos);
        } catch (Exception e) {
            mostrarError(vista, "Error al cargar distribuciones", e);
        }
    }

    private void registrar() {
        Integer idLote = VistaUtil.idDeCombo(vista.getCbLote().getSelectedItem());
        Integer idPunto = VistaUtil.idDeCombo(vista.getCbPunto().getSelectedItem());
        Integer idUsuario = enteroDe(vista.getTxtIdUsuario().getText());
        Integer cantidad = enteroDe(vista.getTxtCantidad().getText());

        if (idLote == null || idPunto == null || idUsuario == null || cantidad == null) {
            mostrarInformacion(vista, "Seleccione lote y punto de venta; ingrese usuario y cantidad.");
            return;
        }
        if (cantidad <= 0) {
            mostrarInformacion(vista, "La cantidad debe ser mayor a cero.");
            return;
        }

        try {
            LotesEntity lote = new LotesEntity();
            lote.setIdLote(idLote);
            PuntosVentaEntity punto = new PuntosVentaEntity();
            punto.setIdPunto(idPunto);
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setIdUsuario(idUsuario);

            DistribucionesEntity distribucion = new DistribucionesEntity();
            distribucion.setLote(lote);
            distribucion.setPuntoVenta(punto);
            distribucion.setUsuario(usuario);
            distribucion.setFechaEnvio(fechaHoraDe(vista.getTxtFechaEnvio().getText()));
            distribucion.setCantidadEnviada(cantidad);
            distribucion.setEstado((String) vista.getCbEstado().getSelectedItem());

            Integer id = service.insertar(distribucion);
            mostrarInformacion(vista, "Distribución registrada con ID " + id);
            cargarDistribuciones();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar distribución", e);
        }
    }
}
