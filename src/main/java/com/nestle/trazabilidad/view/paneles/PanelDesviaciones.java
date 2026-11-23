package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.DesviacionesEntity;
import com.nestle.trazabilidad.view.VistaUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** CU-05: registro y seguimiento de desviaciones de calidad. */
public class PanelDesviaciones extends JPanel {

    private final JComboBox<String> cbLote = new JComboBox<>();
    private final JTextField txtIdControl = new JTextField(6);
    private final JTextField txtIdUsuario = new JTextField(6);
    private final JTextField txtFechaDeteccion = new JTextField(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final JTextField txtFuente = new JTextField(14);
    private final JTextField txtDescripcion = new JTextField(20);
    private final JComboBox<String> cbSeveridad = new JComboBox<>(
            new String[]{"BAJA", "MEDIA", "ALTA", "CRITICA"});
    private final JComboBox<String> cbEstado = new JComboBox<>(
            new String[]{"ABIERTA", "EN_ANALISIS", "CERRADA"});
    private final JComboBox<String> cbEstadoAccion = new JComboBox<>(
            new String[]{"ABIERTA", "EN_ANALISIS", "CERRADA"});

    private final JButton btnRegistrar = new JButton("Registrar desviación");
    private final JButton btnActualizarLista = new JButton("Actualizar lista");
    private final JButton btnCambiarEstado = new JButton("Cambiar estado");

    private final DefaultTableModel modeloTabla = VistaUtil.modeloTabla(new String[]{
            "ID", "Lote", "Fecha", "Fuente", "Descripción", "Severidad", "Estado"
    });
    private final JTable tabla = new JTable(modeloTabla);

    private List<DesviacionesEntity> desviaciones = List.of();

    public PanelDesviaciones() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nueva desviación"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        VistaUtil.agregarCampo(form, gbc, 0, 0, "Lote:", cbLote);
        VistaUtil.agregarCampo(form, gbc, 0, 2, "ID Control (opcional):", txtIdControl);
        VistaUtil.agregarCampo(form, gbc, 1, 0, "ID Usuario:", txtIdUsuario);
        VistaUtil.agregarCampo(form, gbc, 1, 2, "Fecha detección:", txtFechaDeteccion);
        VistaUtil.agregarCampo(form, gbc, 2, 0, "Fuente:", txtFuente);
        VistaUtil.agregarCampo(form, gbc, 2, 2, "Descripción:", txtDescripcion);
        VistaUtil.agregarCampo(form, gbc, 3, 0, "Severidad:", cbSeveridad);
        VistaUtil.agregarCampo(form, gbc, 3, 2, "Estado:", cbEstado);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        form.add(VistaUtil.panelBotones(btnRegistrar, btnActualizarLista), gbc);
        add(form, BorderLayout.NORTH);

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.add(new JLabel("Nuevo estado:"));
        acciones.add(cbEstadoAccion);
        acciones.add(btnCambiarEstado);
        add(acciones, BorderLayout.SOUTH);
    }

    public void setLotes(List<String> etiquetas) {
        cbLote.removeAllItems();
        for (String e : etiquetas) cbLote.addItem(e);
    }

    public void setDesviaciones(List<DesviacionesEntity> desviaciones) {
        this.desviaciones = desviaciones;
        modeloTabla.setRowCount(0);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (DesviacionesEntity d : desviaciones) {
            modeloTabla.addRow(new Object[]{
                    d.getIdDesviacion(),
                    d.getLote() != null ? d.getLote().getIdLote() : "",
                    d.getFechaDeteccion() != null ? d.getFechaDeteccion().format(f) : "",
                    d.getFuente(), d.getDescripcion(), d.getSeveridad(), d.getEstado()
            });
        }
    }

    public DesviacionesEntity getDesviacionSeleccionada() {
        int fila = tabla.getSelectedRow();
        return fila < 0 || fila >= desviaciones.size() ? null : desviaciones.get(fila);
    }

    public JComboBox<String> getCbLote() { return cbLote; }
    public JTextField getTxtIdControl() { return txtIdControl; }
    public JTextField getTxtIdUsuario() { return txtIdUsuario; }
    public JTextField getTxtFechaDeteccion() { return txtFechaDeteccion; }
    public JTextField getTxtFuente() { return txtFuente; }
    public JTextField getTxtDescripcion() { return txtDescripcion; }
    public JComboBox<String> getCbSeveridad() { return cbSeveridad; }
    public JComboBox<String> getCbEstado() { return cbEstado; }
    public JComboBox<String> getCbEstadoAccion() { return cbEstadoAccion; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnActualizarLista() { return btnActualizarLista; }
    public JButton getBtnCambiarEstado() { return btnCambiarEstado; }
}
