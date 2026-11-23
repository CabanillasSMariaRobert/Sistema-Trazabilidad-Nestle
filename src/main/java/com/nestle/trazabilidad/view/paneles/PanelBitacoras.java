package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.BitacoresOperacionesEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Panel de vista para consultar la bitácora de operaciones con filtros. */
public class PanelBitacoras extends JPanel {

    private final JTextField txtIdUsuario = new JTextField(6);
    private final JTextField txtDesde = new JTextField(12);
    private final JTextField txtHasta = new JTextField(12);
    private final JTextField txtAccion = new JTextField(10);
    private final JTextField txtEntidad = new JTextField(10);
    private final JTextField txtIdentificador = new JTextField(10);

    private final JButton btnBuscar = new JButton("Buscar");
    private final JButton btnLimpiar = new JButton("Limpiar filtros");

    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    public PanelBitacoras() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel filtros = new JPanel(new GridBagLayout());
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros de búsqueda"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        agregarCampo(filtros, gbc, 0, 0, "ID Usuario:", txtIdUsuario);
        agregarCampo(filtros, gbc, 0, 2, "Desde:", txtDesde);
        agregarCampo(filtros, gbc, 0, 4, "Hasta:", txtHasta);
        agregarCampo(filtros, gbc, 1, 0, "Acción:", txtAccion);
        agregarCampo(filtros, gbc, 1, 2, "Entidad:", txtEntidad);
        agregarCampo(filtros, gbc, 1, 4, "ID Registro:", txtIdentificador);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(btnBuscar);
        botones.add(btnLimpiar);
        filtros.add(botones, gbc);

        add(filtros, BorderLayout.NORTH);

        String[] columnas = {"ID", "Fecha/Hora", "ID Usuario", "Acción", "Entidad", "ID Registro", "Valores nuevos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc,
                              int fila, int columna, String etiqueta, JTextField campo) {
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = columna + 1;
        panel.add(campo, gbc);
    }

    public void setOperaciones(List<BitacoresOperacionesEntity> operaciones) {
        modeloTabla.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (BitacoresOperacionesEntity o : operaciones) {
            modeloTabla.addRow(new Object[]{
                    o.getIdOperacion(),
                    o.getFechaHora() != null ? o.getFechaHora().format(formatter) : "",
                    o.getUsuario() != null ? o.getUsuario().getIdUsuario() : "",
                    o.getAccion(),
                    o.getEntidadAfectada(),
                    o.getIdentificadorRegistro(),
                    o.getValoresNuevos()
            });
        }
    }

    public void limpiarFiltros() {
        txtIdUsuario.setText("");
        txtDesde.setText("");
        txtHasta.setText("");
        txtAccion.setText("");
        txtEntidad.setText("");
        txtIdentificador.setText("");
    }

    public JTextField getTxtIdUsuario() { return txtIdUsuario; }
    public JTextField getTxtDesde() { return txtDesde; }
    public JTextField getTxtHasta() { return txtHasta; }
    public JTextField getTxtAccion() { return txtAccion; }
    public JTextField getTxtEntidad() { return txtEntidad; }
    public JTextField getTxtIdentificador() { return txtIdentificador; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
}
