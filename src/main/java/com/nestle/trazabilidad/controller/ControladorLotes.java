package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.ProductosEntity;
import com.nestle.trazabilidad.service.LoteService;
import com.nestle.trazabilidad.service.ProductoService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.view.VistaUtil;
import com.nestle.trazabilidad.view.paneles.PanelLotes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Controller del panel de lotes (CU-02 y cambio de estado). */
public class ControladorLotes extends ControladorBase {

    private final PanelLotes vista;
    private final LoteService loteService = ServiceFactory.lote();
    private final ProductoService productoService = ServiceFactory.producto();

    public ControladorLotes(PanelLotes vista) {
        this.vista = vista;

        vista.getBtnActualizar().addActionListener(e -> cargarLotes());
        vista.getBtnCambiarEstado().addActionListener(e -> cambiarEstado());
        vista.getBtnRegistrarLote().addActionListener(e -> registrarLote());
    }

    public void cargarLotes() {
        try {
            vista.setLotes(loteService.listarTodos());
            cargarProductos();
        } catch (Exception e) {
            mostrarError(vista, "Error al cargar lotes", e);
        }
    }

    private void cargarProductos() {
        try {
            List<String> etiquetas = new ArrayList<>();
            for (ProductosEntity p : productoService.listarTodos()) {
                etiquetas.add(p.getIdProducto() + " - " + p.getNombre());
            }
            vista.setProductos(etiquetas);
        } catch (Exception e) {
            mostrarError(vista, "Error al cargar productos", e);
        }
    }

    private void registrarLote() {
        Integer idProducto = VistaUtil.idDeCombo(vista.getCbProducto().getSelectedItem());
        if (idProducto == null) {
            mostrarInformacion(vista, "Seleccione un producto.");
            return;
        }
        try {
            LocalDate fabricacion = fechaDe(vista.getTxtFechaFabricacion().getText());
            LocalDate vencimiento = fechaDe(vista.getTxtFechaVencimiento().getText());

            if (fabricacion != null && vencimiento != null && !vencimiento.isAfter(fabricacion)) {
                mostrarInformacion(vista, "La fecha de vencimiento debe ser posterior a la de fabricación.");
                return;
            }

            ProductosEntity producto = new ProductosEntity();
            producto.setIdProducto(idProducto);

            LotesEntity lote = new LotesEntity();
            lote.setProducto(producto);
            lote.setCodigoLote(vista.getTxtCodigoLote().getText().trim());
            lote.setFechaFabricacion(fabricacion);
            lote.setFechaVencimiento(vencimiento);
            lote.setPlanta(vista.getTxtPlanta().getText().trim());
            lote.setEstado((String) vista.getCbEstadoNuevo().getSelectedItem());

            Integer id = loteService.insertar(lote);
            mostrarInformacion(vista, "Lote registrado con ID " + id);
            vista.limpiarFormulario();
            cargarLotes();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar lote", e);
        }
    }

    private void cambiarEstado() {
        LotesEntity lote = vista.getLoteSeleccionado();
        if (lote == null) {
            mostrarInformacion(vista, "Seleccione un lote en la tabla.");
            return;
        }
        String nuevoEstado = (String) vista.getCbEstado().getSelectedItem();
        if (nuevoEstado == null) {
            mostrarInformacion(vista, "Seleccione un estado.");
            return;
        }
        try {
            boolean ok = loteService.actualizarEstado(lote.getIdLote(), nuevoEstado);
            if (ok) {
                mostrarInformacion(vista, "Estado del lote actualizado a " + nuevoEstado);
                cargarLotes();
            } else {
                mostrarInformacion(vista, "No se pudo actualizar el estado del lote.");
            }
        } catch (Exception e) {
            mostrarError(vista, "Error al actualizar estado", e);
        }
    }
}
