package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.DistribucionesEntity;
import com.nestle.trazabilidad.view.VistaUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** CU-08: registro de movimientos de distribución. */
public class PanelDistribuciones extends JPanel {

    private final JComboBox<String> cbLote = new JComboBox<>();
    private final JComboBox<String> cbPunto = new JComboBox<>();
    private final JTextField txtIdUsuario = new JTextField(6);
    private final JTextField txtFechaEnvio = new JTextField(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final JTextField txtCantidad = new JTextField(8);
    private final JComboBox<String> cbEstado = new JComboBox<>(new String[]{
            "ENVIADA", "EN_RETIRO", "RETIRO_PARCIAL", "RETIRADA"
    });

    private final JButton btnRegistrar = new JButton("Registrar distribución");
    private final JButton btnActualizarLista = new JButton("Actualizar lista");

    private final DefaultTableModel modeloTabla = VistaUtil.modeloTabla(new String[]{
            "ID", "Lote", "Punto", "Fecha envío", "Cantidad", "Estado"
    });
    private final JTable tabla = new JTable(modeloTabla);

    public PanelDistribuciones() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nueva distribución"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        VistaUtil.agregarCampo(form, gbc, 0, 0, "Lote:", cbLote);
        VistaUtil.agregarCampo(form, gbc, 0, 2, "Punto de venta:", cbPunto);
        VistaUtil.agregarCampo(form, gbc, 1, 0, "ID Usuario:", txtIdUsuario);
        VistaUtil.agregarCampo(form, gbc, 1, 2, "Fecha envío:", txtFechaEnvio);
        VistaUtil.agregarCampo(form, gbc, 2, 0, "Cantidad:", txtCantidad);
        VistaUtil.agregarCampo(form, gbc, 2, 2, "Estado:", cbEstado);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        form.add(VistaUtil.panelBotones(btnRegistrar, btnActualizarLista), gbc);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    public void setLotes(List<String> etiquetas) {
        cbLote.removeAllItems();
        for (String e : etiquetas) cbLote.addItem(e);
    }

    public void setPuntos(List<String> etiquetas) {
        cbPunto.removeAllItems();
        for (String e : etiquetas) cbPunto.addItem(e);
    }

    public void setDistribuciones(List<DistribucionesEntity> distribuciones) {
        modeloTabla.setRowCount(0);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (DistribucionesEntity d : distribuciones) {
            modeloTabla.addRow(new Object[]{
                    d.getIdDistribucion(),
                    d.getLote() != null ? d.getLote().getIdLote() : "",
                    d.getPuntoVenta() != null ? d.getPuntoVenta().getIdPunto() : "",
                    d.getFechaEnvio() != null ? d.getFechaEnvio().format(f) : "",
                    d.getCantidadEnviada(), d.getEstado()
            });
        }
    }

    public JComboBox<String> getCbLote() { return cbLote; }
    public JComboBox<String> getCbPunto() { return cbPunto; }
    public JTextField getTxtIdUsuario() { return txtIdUsuario; }
    public JTextField getTxtFechaEnvio() { return txtFechaEnvio; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JComboBox<String> getCbEstado() { return cbEstado; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnActualizarLista() { return btnActualizarLista; }
}
