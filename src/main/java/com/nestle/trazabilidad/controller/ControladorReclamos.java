package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.entity.AtencionesReclamosEntity;
import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.ProductosEntity;
import com.nestle.trazabilidad.entity.ReclamosEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;
import com.nestle.trazabilidad.service.AtencionReclamoService;
import com.nestle.trazabilidad.service.LoteService;
import com.nestle.trazabilidad.service.ProductoService;
import com.nestle.trazabilidad.service.ReclamoService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.view.paneles.PanelReclamos;

import java.util.ArrayList;
import java.util.List;

/** Controller de reclamos y atenciones (CU-10 y CU-11). */
public class ControladorReclamos extends ControladorBase {

    private final PanelReclamos vista;
    private final ReclamoService reclamoService = ServiceFactory.reclamo();
    private final AtencionReclamoService atencionService = ServiceFactory.atencionReclamo();
    private final ProductoService productoService = ServiceFactory.producto();
    private final LoteService loteService = ServiceFactory.lote();

    public ControladorReclamos(PanelReclamos vista) {
        this.vista = vista;
        vista.getBtnActualizar().addActionListener(e -> cargarReclamos());
        vista.getBtnRegistrar().addActionListener(e -> registrarReclamo());
        vista.getBtnCambiarEstado().addActionListener(e -> cambiarEstado());
        vista.getBtnRegistrarAtencion().addActionListener(e -> registrarAtencion());
    }

    public void cargarReclamos() {
        try {
            vista.setReclamos(reclamoService.listarTodos());
            vista.setAtenciones(atencionService.listarTodos());

            List<String> productos = new ArrayList<>();
            for (ProductosEntity p : productoService.listarTodos()) {
                productos.add(p.getIdProducto() + " - " + p.getNombre());
            }
            vista.setProductos(productos);

            List<String> lotes = new ArrayList<>();
            for (LotesEntity l : loteService.listarTodos()) {
                lotes.add(l.getIdLote() + " - " + l.getCodigoLote());
            }
            vista.setLotes(lotes);
        } catch (Exception e) {
            mostrarError(vista, "Error al cargar reclamos", e);
        }
    }

    private void registrarReclamo() {
        Integer idProducto = vista.getIdProductoSeleccionado();
        Integer idLote = vista.getIdLoteSeleccionado();
        Integer idUsuario = enteroDe(vista.getTxtIdUsuarioRegistro().getText());
        if (idProducto == null || idLote == null || idUsuario == null) {
            mostrarInformacion(vista, "Seleccione producto y lote; ingrese el ID del usuario que registra.");
            return;
        }
        try {
            ProductosEntity producto = new ProductosEntity();
            producto.setIdProducto(idProducto);
            LotesEntity lote = new LotesEntity();
            lote.setIdLote(idLote);
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setIdUsuario(idUsuario);

            ReclamosEntity reclamo = new ReclamosEntity();
            reclamo.setProducto(producto);
            reclamo.setLote(lote);
            reclamo.setUsuarioRegistro(usuario);
            reclamo.setFechaRegistro(fechaHoraDe(vista.getTxtFechaRegistro().getText()));
            reclamo.setNombreConsumidor(vista.getTxtNombreConsumidor().getText().trim());
            reclamo.setContacto(vista.getTxtContacto().getText().trim());
            reclamo.setMotivo(vista.getTxtMotivo().getText().trim());
            reclamo.setDescripcion(vista.getTxtDescripcion().getText().trim());
            reclamo.setEstado((String) vista.getCbEstado().getSelectedItem());

            Integer id = reclamoService.insertar(reclamo);
            mostrarInformacion(vista, "Reclamo registrado con ID " + id);
            cargarReclamos();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar reclamo", e);
        }
    }

    private void cambiarEstado() {
        ReclamosEntity reclamo = vista.getReclamoSeleccionado();
        if (reclamo == null) {
            mostrarInformacion(vista, "Seleccione un reclamo en la tabla.");
            return;
        }
        String estado = (String) vista.getCbEstadoAccion().getSelectedItem();
        try {
            if (reclamoService.actualizarEstado(reclamo.getIdReclamo(), estado)) {
                mostrarInformacion(vista, "Estado actualizado a " + estado);
                cargarReclamos();
            } else {
                mostrarInformacion(vista, "No se pudo actualizar el estado.");
            }
        } catch (Exception e) {
            mostrarError(vista, "Error al cambiar estado", e);
        }
    }

    private void registrarAtencion() {
        Integer idReclamo = vista.getIdReclamoSeleccionado();
        Integer idUsuario = enteroDe(vista.getTxtIdUsuarioAtencion().getText());
        if (idReclamo == null || idUsuario == null) {
            mostrarInformacion(vista, "Seleccione un reclamo e ingrese el ID del usuario.");
            return;
        }
        try {
            ReclamosEntity reclamo = new ReclamosEntity();
            reclamo.setIdReclamo(idReclamo);
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setIdUsuario(idUsuario);

            AtencionesReclamosEntity atencion = new AtencionesReclamosEntity();
            atencion.setReclamo(reclamo);
            atencion.setUsuario(usuario);
            atencion.setFechaAtencion(fechaHoraDe(vista.getTxtFechaAtencion().getText()));
            atencion.setAccionRealizada(vista.getTxtAccion().getText().trim());
            atencion.setRespuesta(vista.getTxtRespuesta().getText().trim());

            Integer id = atencionService.insertar(atencion);
            mostrarInformacion(vista, "Atención registrada con ID " + id);
            cargarReclamos();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar atención", e);
        }
    }
}
