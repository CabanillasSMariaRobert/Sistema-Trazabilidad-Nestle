package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.ControlesCalidadEntity;
import com.nestle.trazabilidad.view.VistaUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** CU-04: registro de controles de calidad. */
public class PanelControlesCalidad extends JPanel {

    private final JComboBox<String> cbLote = new JComboBox<>();
    private final JTextField txtIdUsuario = new JTextField(6);
    private final JTextField txtFechaControl = new JTextField(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final JTextField txtParametro = new JTextField(14);
    private final JTextField txtValorObtenido = new JTextField(14);
    private final JComboBox<String> cbResultado = new JComboBox<>(new String[]{"CONFORME", "NO_CONFORME"});
    private final JTextField txtObservaciones = new JTextField(18);

    private final JButton btnRegistrar = new JButton("Registrar control");
    private final JButton btnActualizarLista = new JButton("Actualizar lista");

    private final DefaultTableModel modeloTabla = VistaUtil.modeloTabla(new String[]{
            "ID", "Lote", "Fecha", "Parámetro", "Valor", "Resultado", "Observaciones"
    });
    private final JTable tabla = new JTable(modeloTabla);

    public PanelControlesCalidad() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nuevo control de calidad"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        VistaUtil.agregarCampo(form, gbc, 0, 0, "Lote:", cbLote);
        VistaUtil.agregarCampo(form, gbc, 0, 2, "ID Usuario:", txtIdUsuario);
        VistaUtil.agregarCampo(form, gbc, 1, 0, "Fecha (AAAA-MM-DD HH:MM):", txtFechaControl);
        VistaUtil.agregarCampo(form, gbc, 1, 2, "Parámetro:", txtParametro);
        VistaUtil.agregarCampo(form, gbc, 2, 0, "Valor obtenido:", txtValorObtenido);
        VistaUtil.agregarCampo(form, gbc, 2, 2, "Resultado:", cbResultado);
        VistaUtil.agregarCampo(form, gbc, 3, 0, "Observaciones:", txtObservaciones);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        form.add(VistaUtil.panelBotones(btnRegistrar, btnActualizarLista), gbc);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    public void setLotes(List<String> etiquetas) {
        cbLote.removeAllItems();
        for (String e : etiquetas) cbLote.addItem(e);
    }

    public void setControles(List<ControlesCalidadEntity> controles) {
        modeloTabla.setRowCount(0);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (ControlesCalidadEntity c : controles) {
            modeloTabla.addRow(new Object[]{
                    c.getIdControl(),
                    c.getLote() != null ? c.getLote().getIdLote() : "",
                    c.getFechaControl() != null ? c.getFechaControl().format(f) : "",
                    c.getParametro(), c.getValorObtenido(), c.getResultado(), c.getObservaciones()
            });
        }
    }

    public JComboBox<String> getCbLote() { return cbLote; }
    public JTextField getTxtIdUsuario() { return txtIdUsuario; }
    public JTextField getTxtFechaControl() { return txtFechaControl; }
    public JTextField getTxtParametro() { return txtParametro; }
    public JTextField getTxtValorObtenido() { return txtValorObtenido; }
    public JComboBox<String> getCbResultado() { return cbResultado; }
    public JTextField getTxtObservaciones() { return txtObservaciones; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnActualizarLista() { return btnActualizarLista; }
}
