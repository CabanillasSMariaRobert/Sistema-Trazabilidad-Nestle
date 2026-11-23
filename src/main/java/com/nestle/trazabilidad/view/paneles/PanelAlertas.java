package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.AlertasSanitariasEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Panel de vista para el registro y consulta de alertas sanitarias. */
public class PanelAlertas extends JPanel {

    private final JTextField txtNroComunicado = new JTextField(14);
    private final JTextField txtOrigen = new JTextField(14);
    private final JTextField txtDescripcion = new JTextField(14);
    private final JTextField txtFechaEmision = new JTextField(10);
    private final JTextField txtIdUsuario = new JTextField(6);

    private final JButton btnRegistrar = new JButton("Registrar alerta");
    private final JButton btnAsociarLote = new JButton("Asociar lote");
    private final JButton btnCerrar = new JButton("Cerrar alerta");
    private final JButton btnActualizar = new JButton("Actualizar lista");

    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private List<AlertasSanitariasEntity> alertasActuales = List.of();

    public PanelAlertas() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Formulario ----
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Nueva alerta sanitaria"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        agregarCampo(formulario, gbc, 0, "N° Comunicado:", txtNroComunicado);
        agregarCampo(formulario, gbc, 1, "Origen:", txtOrigen);
        agregarCampo(formulario, gbc, 2, "Descripción:", txtDescripcion);
        agregarCampo(formulario, gbc, 3, "Fecha emisión (AAAA-MM-DD):", txtFechaEmision);
        agregarCampo(formulario, gbc, 4, "ID Usuario:", txtIdUsuario);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(btnRegistrar);
        botones.add(btnActualizar);
        formulario.add(botones, gbc);

        add(formulario, BorderLayout.NORTH);

        // ---- Tabla ----
        String[] columnas = {"ID", "N° Comunicado", "Origen", "Fecha Emisión", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ---- Acciones sobre la fila seleccionada ----
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.add(btnAsociarLote);
        acciones.add(btnCerrar);
        add(acciones, BorderLayout.SOUTH);
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila,
                              String etiqueta, JTextField campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    public void setAlertas(List<AlertasSanitariasEntity> alertas) {
        this.alertasActuales = alertas;
        modeloTabla.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (AlertasSanitariasEntity a : alertas) {
            modeloTabla.addRow(new Object[]{
                    a.getIdAlerta(),
                    a.getNroComunicado(),
                    a.getOrigen(),
                    a.getFechaEmision() != null ? a.getFechaEmision().format(formatter) : "",
                    a.getEstado() != null ? a.getEstado().name() : ""
            });
        }
    }

    public AlertasSanitariasEntity getAlertaSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= alertasActuales.size()) {
            return null;
        }
        return alertasActuales.get(fila);
    }

    public void limpiarFormulario() {
        txtNroComunicado.setText("");
        txtOrigen.setText("");
        txtDescripcion.setText("");
        txtFechaEmision.setText("");
        txtIdUsuario.setText("");
    }

    public JTextField getTxtNroComunicado() { return txtNroComunicado; }
    public JTextField getTxtOrigen() { return txtOrigen; }
    public JTextField getTxtDescripcion() { return txtDescripcion; }
    public JTextField getTxtFechaEmision() { return txtFechaEmision; }
    public JTextField getTxtIdUsuario() { return txtIdUsuario; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnAsociarLote() { return btnAsociarLote; }
    public JButton getBtnCerrar() { return btnCerrar; }
    public JButton getBtnActualizar() { return btnActualizar; }
}
