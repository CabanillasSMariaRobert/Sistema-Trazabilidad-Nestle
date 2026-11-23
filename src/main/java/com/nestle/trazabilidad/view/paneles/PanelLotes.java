package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.view.VistaUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** CU-02 y consulta de lotes: registro, listado y cambio de estado. */
public class PanelLotes extends JPanel {

    private static final String[] ESTADOS = {
            "PENDIENTE", "LIBERADO", "OBSERVADO", "EN_RETIRO", "RETIRADO"
    };

    private final JComboBox<String> cbProducto = new JComboBox<>();
    private final JTextField txtCodigoLote = new JTextField(14);
    private final JTextField txtFechaFabricacion = new JTextField(12);
    private final JTextField txtFechaVencimiento = new JTextField(12);
    private final JTextField txtPlanta = new JTextField(16);
    private final JComboBox<String> cbEstadoNuevo = new JComboBox<>(ESTADOS);
    private final JButton btnRegistrarLote = new JButton("Registrar lote");

    private final JComboBox<String> cbEstado = new JComboBox<>(ESTADOS);
    private final JButton btnActualizar = new JButton("Actualizar lista");
    private final JButton btnCambiarEstado = new JButton("Cambiar estado");

    private final DefaultTableModel modeloTabla = VistaUtil.modeloTabla(new String[]{
            "ID", "Código", "Producto", "Planta", "Fabricación", "Vencimiento", "Estado"
    });
    private final JTable tabla = new JTable(modeloTabla);

    private List<LotesEntity> lotesActuales = List.of();

    public PanelLotes() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Formulario de registro ----
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Nuevo lote"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        VistaUtil.agregarCampo(formulario, gbc, 0, 0, "Producto:", cbProducto);
        VistaUtil.agregarCampo(formulario, gbc, 0, 2, "Código de lote:", txtCodigoLote);
        VistaUtil.agregarCampo(formulario, gbc, 1, 0, "Fabricación (AAAA-MM-DD):", txtFechaFabricacion);
        VistaUtil.agregarCampo(formulario, gbc, 1, 2, "Vencimiento (AAAA-MM-DD):", txtFechaVencimiento);
        VistaUtil.agregarCampo(formulario, gbc, 2, 0, "Planta:", txtPlanta);
        VistaUtil.agregarCampo(formulario, gbc, 2, 2, "Estado:", cbEstadoNuevo);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        formulario.add(VistaUtil.panelBotones(btnRegistrarLote), gbc);
        add(formulario, BorderLayout.NORTH);

        // ---- Tabla ----
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ---- Acciones ----
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.setBorder(BorderFactory.createTitledBorder("Cambiar estado del lote seleccionado"));
        acciones.add(new JLabel("Nuevo estado:"));
        acciones.add(cbEstado);
        acciones.add(btnCambiarEstado);
        acciones.add(Box.createHorizontalStrut(20));
        acciones.add(btnActualizar);
        add(acciones, BorderLayout.SOUTH);
    }

    public void setProductos(List<String> etiquetas) {
        cbProducto.removeAllItems();
        for (String etiqueta : etiquetas) cbProducto.addItem(etiqueta);
    }

    public void setLotes(List<LotesEntity> lotes) {
        this.lotesActuales = lotes;
        modeloTabla.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (LotesEntity l : lotes) {
            modeloTabla.addRow(new Object[]{
                    l.getIdLote(),
                    l.getCodigoLote(),
                    l.getProducto() != null ? l.getProducto().getIdProducto() : "",
                    l.getPlanta(),
                    l.getFechaFabricacion() != null ? l.getFechaFabricacion().format(formatter) : "",
                    l.getFechaVencimiento() != null ? l.getFechaVencimiento().format(formatter) : "",
                    l.getEstado()
            });
        }
    }

    public LotesEntity getLoteSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= lotesActuales.size()) return null;
        return lotesActuales.get(fila);
    }

    public void limpiarFormulario() {
        txtCodigoLote.setText("");
        txtFechaFabricacion.setText("");
        txtFechaVencimiento.setText("");
        txtPlanta.setText("");
    }

    public JComboBox<String> getCbProducto() { return cbProducto; }
    public JTextField getTxtCodigoLote() { return txtCodigoLote; }
    public JTextField getTxtFechaFabricacion() { return txtFechaFabricacion; }
    public JTextField getTxtFechaVencimiento() { return txtFechaVencimiento; }
    public JTextField getTxtPlanta() { return txtPlanta; }
    public JComboBox<String> getCbEstadoNuevo() { return cbEstadoNuevo; }
    public JButton getBtnRegistrarLote() { return btnRegistrarLote; }
    public JComboBox<String> getCbEstado() { return cbEstado; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnCambiarEstado() { return btnCambiarEstado; }
}
