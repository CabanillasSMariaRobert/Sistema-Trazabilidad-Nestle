package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.ProductosEntity;
import com.nestle.trazabilidad.view.VistaUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** CU-01: registro y mantenimiento de productos. */
public class PanelProductos extends JPanel {

    private final JTextField txtCodigo = new JTextField(14);
    private final JTextField txtNombre = new JTextField(14);
    private final JTextField txtDescripcion = new JTextField(18);
    private final JTextField txtCategoria = new JTextField(14);
    private final JComboBox<String> cbEstado = new JComboBox<>(new String[]{"ACTIVO", "INACTIVO"});

    private final JButton btnRegistrar = new JButton("Registrar producto");
    private final JButton btnActualizar = new JButton("Guardar cambios");
    private final JButton btnActualizarLista = new JButton("Actualizar lista");

    private final DefaultTableModel modeloTabla = VistaUtil.modeloTabla(
            new String[]{"ID", "Código", "Nombre", "Categoría", "Estado"});
    private final JTable tabla = new JTable(modeloTabla);

    private List<ProductosEntity> productos = List.of();

    public PanelProductos() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos del producto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        VistaUtil.agregarCampo(form, gbc, 0, 0, "Código:", txtCodigo);
        VistaUtil.agregarCampo(form, gbc, 0, 2, "Nombre:", txtNombre);
        VistaUtil.agregarCampo(form, gbc, 1, 0, "Descripción:", txtDescripcion);
        VistaUtil.agregarCampo(form, gbc, 1, 2, "Categoría:", txtCategoria);
        VistaUtil.agregarCampo(form, gbc, 2, 0, "Estado:", cbEstado);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        form.add(VistaUtil.panelBotones(btnRegistrar, btnActualizar, btnActualizarLista), gbc);

        add(form, BorderLayout.NORTH);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    public void setProductos(List<ProductosEntity> productos) {
        this.productos = productos;
        modeloTabla.setRowCount(0);
        for (ProductosEntity p : productos) {
            modeloTabla.addRow(new Object[]{
                    p.getIdProducto(), p.getCodigo(), p.getNombre(), p.getCategoria(), p.getEstado()
            });
        }
    }

    public ProductosEntity getProductoSeleccionado() {
        int fila = tabla.getSelectedRow();
        return fila < 0 || fila >= productos.size() ? null : productos.get(fila);
    }

    public void cargarEnFormulario(ProductosEntity p) {
        txtCodigo.setText(p.getCodigo() != null ? p.getCodigo() : "");
        txtNombre.setText(p.getNombre() != null ? p.getNombre() : "");
        txtDescripcion.setText(p.getDescripcion() != null ? p.getDescripcion() : "");
        txtCategoria.setText(p.getCategoria() != null ? p.getCategoria() : "");
        cbEstado.setSelectedItem("ACTIVO".equalsIgnoreCase(p.getEstado()) ? "ACTIVO" : "INACTIVO");
    }

    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtCategoria.setText("");
        cbEstado.setSelectedItem("ACTIVO");
    }

    public JTable getTabla() { return tabla; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtDescripcion() { return txtDescripcion; }
    public JTextField getTxtCategoria() { return txtCategoria; }
    public JComboBox<String> getCbEstado() { return cbEstado; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnActualizarLista() { return btnActualizarLista; }
}
