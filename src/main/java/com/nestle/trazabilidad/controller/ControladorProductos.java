package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.entity.ProductosEntity;
import com.nestle.trazabilidad.service.ProductoService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.view.paneles.PanelProductos;

/** Controller del panel de productos (CU-01). */
public class ControladorProductos extends ControladorBase {

    private final PanelProductos vista;
    private final ProductoService service = ServiceFactory.producto();

    public ControladorProductos(PanelProductos vista) {
        this.vista = vista;

        vista.getBtnRegistrar().addActionListener(e -> registrar());
        vista.getBtnActualizar().addActionListener(e -> actualizar());
        vista.getBtnActualizarLista().addActionListener(e -> cargarProductos());
        tablaDobleClic();
    }

    public void cargarProductos() {
        try {
            vista.setProductos(service.listarTodos());
        } catch (Exception e) {
            mostrarError(vista, "Error al cargar productos", e);
        }
    }

    private void registrar() {
        try {
            ProductosEntity p = new ProductosEntity();
            p.setCodigo(vista.getTxtCodigo().getText().trim());
            p.setNombre(vista.getTxtNombre().getText().trim());
            p.setDescripcion(vista.getTxtDescripcion().getText().trim());
            p.setCategoria(vista.getTxtCategoria().getText().trim());
            p.setEstado((String) vista.getCbEstado().getSelectedItem());

            Integer id = service.insertar(p);
            mostrarInformacion(vista, "Producto registrado con ID " + id);
            vista.limpiarFormulario();
            cargarProductos();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar producto", e);
        }
    }

    private void actualizar() {
        ProductosEntity seleccionado = vista.getProductoSeleccionado();
        if (seleccionado == null) {
            mostrarInformacion(vista, "Seleccione un producto de la tabla.");
            return;
        }
        try {
            seleccionado.setCodigo(vista.getTxtCodigo().getText().trim());
            seleccionado.setNombre(vista.getTxtNombre().getText().trim());
            seleccionado.setDescripcion(vista.getTxtDescripcion().getText().trim());
            seleccionado.setCategoria(vista.getTxtCategoria().getText().trim());
            seleccionado.setEstado((String) vista.getCbEstado().getSelectedItem());

            if (service.actualizar(seleccionado)) {
                mostrarInformacion(vista, "Producto actualizado.");
                cargarProductos();
            } else {
                mostrarInformacion(vista, "No se pudo actualizar el producto.");
            }
        } catch (Exception e) {
            mostrarError(vista, "Error al actualizar producto", e);
        }
    }

    private void tablaDobleClic() {
        // Doble clic en la tabla carga el registro en el formulario.
        vista.getTabla().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    ProductosEntity p = vista.getProductoSeleccionado();
                    if (p != null) vista.cargarEnFormulario(p);
                }
            }
        });
    }
}
