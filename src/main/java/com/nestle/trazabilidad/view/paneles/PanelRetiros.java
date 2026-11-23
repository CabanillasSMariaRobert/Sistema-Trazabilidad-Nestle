package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.AlertasLotesEntity;
import com.nestle.trazabilidad.entity.DetallesRetiroEntity;
import com.nestle.trazabilidad.entity.DistribucionesEntity;
import com.nestle.trazabilidad.entity.RetirosEntity;
import com.nestle.trazabilidad.view.VistaUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** CU-07 y CU-09: retiros y registración de la recuperación. */
public class PanelRetiros extends JPanel {

    private final JComboBox<String> cbAlertaLote = new JComboBox<>();
    private final JTextField txtIdUsuario = new JTextField(6);
    private final JTextField txtObservaciones = new JTextField(22);
    private final JButton btnIniciar = new JButton("Iniciar retiro");
    private final JButton btnCerrar = new JButton("Cerrar retiro");
    private final JButton btnActualizar = new JButton("Actualizar lista");

    private final JComboBox<String> cbRetiro = new JComboBox<>();
    private final JComboBox<String> cbDistribucion = new JComboBox<>();
    private final JTextField txtIdUsuarioRec = new JTextField(6);
    private final JTextField txtCantidad = new JTextField(8);
    private final JTextField txtFechaRecuperacion = new JTextField(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final JTextField txtObservacionesRec = new JTextField(22);
    private final JButton btnRecuperar = new JButton("Registrar recuperación");

    private final DefaultTableModel modeloRetiros = VistaUtil.modeloTabla(new String[]{
            "ID", "Asociación", "Inicio", "Cierre", "Estado", "Observaciones"
    });
    private final DefaultTableModel modeloDetalles = VistaUtil.modeloTabla(new String[]{
            "ID", "Retiro", "Distribución", "Fecha", "Cantidad", "Observaciones"
    });
    private final JTable tablaRetiros = new JTable(modeloRetiros);
    private final JTable tablaDetalles = new JTable(modeloDetalles);

    private List<RetirosEntity> retiros = List.of();
    private List<AlertasLotesEntity> asociaciones = List.of();
    private List<DistribucionesEntity> distribuciones = List.of();

    public PanelRetiros() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formRetiro = new JPanel(new GridBagLayout());
        formRetiro.setBorder(BorderFactory.createTitledBorder("Retiro preventivo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        VistaUtil.agregarCampo(formRetiro, gbc, 0, 0, "Alerta-Lote:", cbAlertaLote);
        VistaUtil.agregarCampo(formRetiro, gbc, 0, 2, "ID Usuario:", txtIdUsuario);
        VistaUtil.agregarCampo(formRetiro, gbc, 1, 0, "Observaciones:", txtObservaciones);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        formRetiro.add(VistaUtil.panelBotones(btnIniciar, btnCerrar, btnActualizar), gbc);

        JPanel formRecuperacion = new JPanel(new GridBagLayout());
        formRecuperacion.setBorder(BorderFactory.createTitledBorder("Recuperación por punto de venta"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(4, 4, 4, 4);
        gbc2.anchor = GridBagConstraints.WEST;
        VistaUtil.agregarCampo(formRecuperacion, gbc2, 0, 0, "Retiro:", cbRetiro);
        VistaUtil.agregarCampo(formRecuperacion, gbc2, 0, 2, "Distribución:", cbDistribucion);
        VistaUtil.agregarCampo(formRecuperacion, gbc2, 1, 0, "ID Usuario:", txtIdUsuarioRec);
        VistaUtil.agregarCampo(formRecuperacion, gbc2, 1, 2, "Cantidad:", txtCantidad);
        VistaUtil.agregarCampo(formRecuperacion, gbc2, 2, 0, "Fecha recuperación:", txtFechaRecuperacion);
        VistaUtil.agregarCampo(formRecuperacion, gbc2, 2, 2, "Observaciones:", txtObservacionesRec);
        gbc2.gridx = 0;
        gbc2.gridy = 3;
        gbc2.gridwidth = 4;
        formRecuperacion.add(VistaUtil.panelBotones(btnRecuperar), gbc2);

        tablaRetiros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDetalles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTabbedPane pestanas = new JTabbedPane();
        JPanel tablaRetirosPanel = new JPanel(new BorderLayout());
        tablaRetirosPanel.add(formRetiro, BorderLayout.NORTH);
        tablaRetirosPanel.add(new JScrollPane(tablaRetiros), BorderLayout.CENTER);
        pestanas.addTab("Retiros", tablaRetirosPanel);

        JPanel tablaDetallesPanel = new JPanel(new BorderLayout());
        tablaDetallesPanel.add(formRecuperacion, BorderLayout.NORTH);
        tablaDetallesPanel.add(new JScrollPane(tablaDetalles), BorderLayout.CENTER);
        pestanas.addTab("Recuperaciones", tablaDetallesPanel);

        add(pestanas, BorderLayout.CENTER);
    }

    public void setAsociaciones(List<AlertasLotesEntity> asociaciones) {
        this.asociaciones = asociaciones;
        cbAlertaLote.removeAllItems();
        for (AlertasLotesEntity a : asociaciones) {
            Integer idAlerta = a.getAlerta() != null ? a.getAlerta().getIdAlerta() : null;
            Integer idLote = a.getLote() != null ? a.getLote().getIdLote() : null;
            cbAlertaLote.addItem(a.getIdAlertaLote() + " - Alerta " + idAlerta + " / Lote " + idLote);
        }
    }

    public void setDistribuciones(List<DistribucionesEntity> distribuciones) {
        this.distribuciones = distribuciones;
        cbDistribucion.removeAllItems();
        for (DistribucionesEntity d : distribuciones) {
            Integer idPunto = d.getPuntoVenta() != null ? d.getPuntoVenta().getIdPunto() : null;
            cbDistribucion.addItem(d.getIdDistribucion() + " - Lote "
                    + (d.getLote() != null ? d.getLote().getIdLote() : "-")
                    + " / Punto " + idPunto + " (" + d.getCantidadEnviada() + " und.)");
        }
    }

    public void setRetiros(List<RetirosEntity> retiros) {
        this.retiros = retiros;
        modeloRetiros.setRowCount(0);
        cbRetiro.removeAllItems();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (RetirosEntity r : retiros) {
            modeloRetiros.addRow(new Object[]{
                    r.getIdRetiro(),
                    r.getAlertaLote() != null ? r.getAlertaLote().getIdAlertaLote() : "",
                    r.getFechaInicio() != null ? r.getFechaInicio().format(f) : "",
                    r.getFechaCierre() != null ? r.getFechaCierre().format(f) : "",
                    r.getEstado(), r.getObservaciones()
            });
            cbRetiro.addItem(r.getIdRetiro() + " - " + r.getEstado());
        }
    }

    public void setDetalles(List<DetallesRetiroEntity> detalles) {
        modeloDetalles.setRowCount(0);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (DetallesRetiroEntity d : detalles) {
            modeloDetalles.addRow(new Object[]{
                    d.getIdDetalleRetiro(),
                    d.getRetiro() != null ? d.getRetiro().getIdRetiro() : "",
                    d.getDistribucion() != null ? d.getDistribucion().getIdDistribucion() : "",
                    d.getFechaRecuperacion() != null ? d.getFechaRecuperacion().format(f) : "",
                    d.getCantidadRecuperada(), d.getObservaciones()
            });
        }
    }

    public Integer getIdAlertaLoteSeleccionado() {
        return VistaUtil.idDeCombo(cbAlertaLote.getSelectedItem());
    }

    public Integer getIdRetiroSeleccionado() {
        return VistaUtil.idDeCombo(cbRetiro.getSelectedItem());
    }

    public Integer getIdDistribucionSeleccionada() {
        return VistaUtil.idDeCombo(cbDistribucion.getSelectedItem());
    }

    public RetirosEntity getRetiroDeTabla() {
        int fila = tablaRetiros.getSelectedRow();
        return fila < 0 || fila >= retiros.size() ? null : retiros.get(fila);
    }

    public JTextField getTxtIdUsuario() { return txtIdUsuario; }
    public JTextField getTxtObservaciones() { return txtObservaciones; }
    public JTextField getTxtIdUsuarioRec() { return txtIdUsuarioRec; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JTextField getTxtFechaRecuperacion() { return txtFechaRecuperacion; }
    public JTextField getTxtObservacionesRec() { return txtObservacionesRec; }
    public JButton getBtnIniciar() { return btnIniciar; }
    public JButton getBtnCerrar() { return btnCerrar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnRecuperar() { return btnRecuperar; }
}
