package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.AtencionesReclamosEntity;
import com.nestle.trazabilidad.entity.ReclamosEntity;
import com.nestle.trazabilidad.view.VistaUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** CU-10 y CU-11: reclamos de consumidores y sus atenciones. */
public class PanelReclamos extends JPanel {

    private static final String[] ESTADOS = {"REGISTRADO", "EN_ATENCION", "RESUELTO", "CERRADO"};

    private final JComboBox<String> cbProducto = new JComboBox<>();
    private final JComboBox<String> cbLote = new JComboBox<>();
    private final JTextField txtIdUsuarioRegistro = new JTextField(6);
    private final JTextField txtFechaRegistro = new JTextField(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final JTextField txtNombreConsumidor = new JTextField(16);
    private final JTextField txtContacto = new JTextField(16);
    private final JTextField txtMotivo = new JTextField(16);
    private final JTextField txtDescripcion = new JTextField(22);
    private final JComboBox<String> cbEstado = new JComboBox<>(ESTADOS);
    private final JButton btnRegistrar = new JButton("Registrar reclamo");
    private final JButton btnActualizar = new JButton("Actualizar lista");
    private final JComboBox<String> cbEstadoAccion = new JComboBox<>(ESTADOS);
    private final JButton btnCambiarEstado = new JButton("Cambiar estado");

    private final JComboBox<String> cbReclamo = new JComboBox<>();
    private final JTextField txtIdUsuarioAtencion = new JTextField(6);
    private final JTextField txtFechaAtencion = new JTextField(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final JTextField txtAccion = new JTextField(18);
    private final JTextField txtRespuesta = new JTextField(22);
    private final JButton btnRegistrarAtencion = new JButton("Registrar atención");

    private final DefaultTableModel modeloReclamos = VistaUtil.modeloTabla(new String[]{
            "ID", "Producto", "Lote", "Consumidor", "Motivo", "Registro", "Estado"
    });
    private final DefaultTableModel modeloAtenciones = VistaUtil.modeloTabla(new String[]{
            "ID", "Reclamo", "Fecha", "Acción", "Respuesta"
    });
    private final JTable tablaReclamos = new JTable(modeloReclamos);
    private final JTable tablaAtenciones = new JTable(modeloAtenciones);

    private List<ReclamosEntity> reclamos = List.of();

    public PanelReclamos() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formReclamo = new JPanel(new GridBagLayout());
        formReclamo.setBorder(BorderFactory.createTitledBorder("Nuevo reclamo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        VistaUtil.agregarCampo(formReclamo, gbc, 0, 0, "Producto:", cbProducto);
        VistaUtil.agregarCampo(formReclamo, gbc, 0, 2, "Lote:", cbLote);
        VistaUtil.agregarCampo(formReclamo, gbc, 1, 0, "ID Usuario registro:", txtIdUsuarioRegistro);
        VistaUtil.agregarCampo(formReclamo, gbc, 1, 2, "Fecha registro:", txtFechaRegistro);
        VistaUtil.agregarCampo(formReclamo, gbc, 2, 0, "Consumidor:", txtNombreConsumidor);
        VistaUtil.agregarCampo(formReclamo, gbc, 2, 2, "Contacto:", txtContacto);
        VistaUtil.agregarCampo(formReclamo, gbc, 3, 0, "Motivo:", txtMotivo);
        VistaUtil.agregarCampo(formReclamo, gbc, 3, 2, "Descripción:", txtDescripcion);
        VistaUtil.agregarCampo(formReclamo, gbc, 4, 0, "Estado:", cbEstado);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        formReclamo.add(VistaUtil.panelBotones(btnRegistrar, btnActualizar), gbc);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.setBorder(BorderFactory.createTitledBorder("Cambiar estado del reclamo seleccionado"));
        acciones.add(new JLabel("Nuevo estado:"));
        acciones.add(cbEstadoAccion);
        acciones.add(btnCambiarEstado);

        JPanel tabReclamos = new JPanel(new BorderLayout());
        tabReclamos.add(formReclamo, BorderLayout.NORTH);
        tabReclamos.add(new JScrollPane(tablaReclamos), BorderLayout.CENTER);
        tabReclamos.add(acciones, BorderLayout.SOUTH);

        JPanel formAtencion = new JPanel(new GridBagLayout());
        formAtencion.setBorder(BorderFactory.createTitledBorder("Nueva atención"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(4, 4, 4, 4);
        gbc2.anchor = GridBagConstraints.WEST;
        VistaUtil.agregarCampo(formAtencion, gbc2, 0, 0, "Reclamo:", cbReclamo);
        VistaUtil.agregarCampo(formAtencion, gbc2, 0, 2, "ID Usuario:", txtIdUsuarioAtencion);
        VistaUtil.agregarCampo(formAtencion, gbc2, 1, 0, "Fecha atención:", txtFechaAtencion);
        VistaUtil.agregarCampo(formAtencion, gbc2, 1, 2, "Acción realizada:", txtAccion);
        VistaUtil.agregarCampo(formAtencion, gbc2, 2, 0, "Respuesta:", txtRespuesta);
        gbc2.gridx = 0;
        gbc2.gridy = 3;
        gbc2.gridwidth = 4;
        formAtencion.add(VistaUtil.panelBotones(btnRegistrarAtencion), gbc2);

        JPanel tabAtenciones = new JPanel(new BorderLayout());
        tabAtenciones.add(formAtencion, BorderLayout.NORTH);
        tabAtenciones.add(new JScrollPane(tablaAtenciones), BorderLayout.CENTER);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Reclamos", tabReclamos);
        pestanas.addTab("Atenciones", tabAtenciones);
        add(pestanas, BorderLayout.CENTER);

        tablaReclamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setProductos(List<String> etiquetas) {
        cbProducto.removeAllItems();
        for (String e : etiquetas) cbProducto.addItem(e);
    }

    public void setLotes(List<String> etiquetas) {
        cbLote.removeAllItems();
        for (String e : etiquetas) cbLote.addItem(e);
    }

    public void setReclamos(List<ReclamosEntity> reclamos) {
        this.reclamos = reclamos;
        modeloReclamos.setRowCount(0);
        cbReclamo.removeAllItems();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (ReclamosEntity r : reclamos) {
            modeloReclamos.addRow(new Object[]{
                    r.getIdReclamo(),
                    r.getProducto() != null ? r.getProducto().getIdProducto() : "",
                    r.getLote() != null ? r.getLote().getIdLote() : "",
                    r.getNombreConsumidor(), r.getMotivo(),
                    r.getFechaRegistro() != null ? r.getFechaRegistro().format(f) : "",
                    r.getEstado()
            });
            cbReclamo.addItem(r.getIdReclamo() + " - " + r.getNombreConsumidor());
        }
    }

    public void setAtenciones(List<AtencionesReclamosEntity> atenciones) {
        modeloAtenciones.setRowCount(0);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (AtencionesReclamosEntity a : atenciones) {
            modeloAtenciones.addRow(new Object[]{
                    a.getIdAtencion(),
                    a.getReclamo() != null ? a.getReclamo().getIdReclamo() : "",
                    a.getFechaAtencion() != null ? a.getFechaAtencion().format(f) : "",
                    a.getAccionRealizada(), a.getRespuesta()
            });
        }
    }

    public ReclamosEntity getReclamoSeleccionado() {
        int fila = tablaReclamos.getSelectedRow();
        return fila < 0 || fila >= reclamos.size() ? null : reclamos.get(fila);
    }

    public Integer getIdProductoSeleccionado() { return VistaUtil.idDeCombo(cbProducto.getSelectedItem()); }
    public Integer getIdLoteSeleccionado() { return VistaUtil.idDeCombo(cbLote.getSelectedItem()); }
    public Integer getIdReclamoSeleccionado() { return VistaUtil.idDeCombo(cbReclamo.getSelectedItem()); }

    public JTextField getTxtIdUsuarioRegistro() { return txtIdUsuarioRegistro; }
    public JTextField getTxtFechaRegistro() { return txtFechaRegistro; }
    public JTextField getTxtNombreConsumidor() { return txtNombreConsumidor; }
    public JTextField getTxtContacto() { return txtContacto; }
    public JTextField getTxtMotivo() { return txtMotivo; }
    public JTextField getTxtDescripcion() { return txtDescripcion; }
    public JComboBox<String> getCbEstado() { return cbEstado; }
    public JComboBox<String> getCbEstadoAccion() { return cbEstadoAccion; }
    public JTextField getTxtIdUsuarioAtencion() { return txtIdUsuarioAtencion; }
    public JTextField getTxtFechaAtencion() { return txtFechaAtencion; }
    public JTextField getTxtAccion() { return txtAccion; }
    public JTextField getTxtRespuesta() { return txtRespuesta; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnCambiarEstado() { return btnCambiarEstado; }
    public JButton getBtnRegistrarAtencion() { return btnRegistrarAtencion; }
}
