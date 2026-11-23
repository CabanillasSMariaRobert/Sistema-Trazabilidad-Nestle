package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.*;
import com.nestle.trazabilidad.model.TrazabilidadLoteModel;
import com.nestle.trazabilidad.view.VistaUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** CU-03: consulta del historial completo de un lote. */
public class PanelTrazabilidad extends JPanel {

    private final JComboBox<String> cbLote = new JComboBox<>();
    private final JButton btnBuscar = new JButton("Consultar trazabilidad");
    private final JTextArea txtResumen = new JTextArea();

    private final DefaultTableModel modeloControles = VistaUtil.modeloTabla(
            new String[]{"ID", "Fecha", "Parámetro", "Valor", "Resultado", "Observaciones"});
    private final DefaultTableModel modeloDesviaciones = VistaUtil.modeloTabla(
            new String[]{"ID", "Fecha", "Fuente", "Descripción", "Severidad", "Estado"});
    private final DefaultTableModel modeloAlertas = VistaUtil.modeloTabla(
            new String[]{"ID", "Comunicado", "Emisión", "Origen", "Descripción", "Estado"});
    private final DefaultTableModel modeloDistribuciones = VistaUtil.modeloTabla(
            new String[]{"ID", "Punto", "Fecha envío", "Cantidad", "Estado"});

    private final DateTimeFormatter fechaHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PanelTrazabilidad() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buscador = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscador.setBorder(BorderFactory.createTitledBorder("Seleccione un lote"));
        buscador.add(new JLabel("Lote:"));
        buscador.add(cbLote);
        buscador.add(btnBuscar);
        add(buscador, BorderLayout.NORTH);

        txtResumen.setEditable(false);
        txtResumen.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Resumen", new JScrollPane(txtResumen));
        pestanas.addTab("Controles de calidad", new JScrollPane(new JTable(modeloControles)));
        pestanas.addTab("Desviaciones", new JScrollPane(new JTable(modeloDesviaciones)));
        pestanas.addTab("Alertas sanitarias", new JScrollPane(new JTable(modeloAlertas)));
        pestanas.addTab("Distribuciones", new JScrollPane(new JTable(modeloDistribuciones)));
        add(pestanas, BorderLayout.CENTER);
    }

    public void setLotes(List<String> etiquetas) {
        cbLote.removeAllItems();
        for (String e : etiquetas) cbLote.addItem(e);
    }

    public Integer getIdLoteSeleccionado() {
        return VistaUtil.idDeCombo(cbLote.getSelectedItem());
    }

    public void setTrazabilidad(TrazabilidadLoteModel t) {
        LotesEntity lote = t.getLote();
        txtResumen.setText(
                "ID lote:          " + lote.getIdLote() + "\n" +
                "Código:           " + lote.getCodigoLote() + "\n" +
                "Producto:         " + (t.getProducto() != null ? t.getProducto().getNombre() : "-") + "\n" +
                "Planta:           " + lote.getPlanta() + "\n" +
                "Fabricación:      " + fmt(lote.getFechaFabricacion()) + "\n" +
                "Vencimiento:      " + fmt(lote.getFechaVencimiento()) + "\n" +
                "Estado:           " + lote.getEstado() + "\n\n" +
                "Controles:        " + t.getControles().size() + "\n" +
                "Desviaciones:     " + t.getDesviaciones().size() + "\n" +
                "Alertas:          " + t.getAlertas().size() + "\n" +
                "Distribuciones:   " + t.getDistribuciones().size()
        );

        modeloControles.setRowCount(0);
        for (ControlesCalidadEntity c : t.getControles()) {
            modeloControles.addRow(new Object[]{
                    c.getIdControl(), fmtH(c.getFechaControl()), c.getParametro(),
                    c.getValorObtenido(), c.getResultado(), c.getObservaciones()
            });
        }

        modeloDesviaciones.setRowCount(0);
        for (DesviacionesEntity d : t.getDesviaciones()) {
            modeloDesviaciones.addRow(new Object[]{
                    d.getIdDesviacion(), fmtH(d.getFechaDeteccion()), d.getFuente(),
                    d.getDescripcion(), d.getSeveridad(), d.getEstado()
            });
        }

        modeloAlertas.setRowCount(0);
        for (AlertasSanitariasEntity a : t.getAlertas()) {
            modeloAlertas.addRow(new Object[]{
                    a.getIdAlerta(), a.getNroComunicado(), fmt(a.getFechaEmision()),
                    a.getOrigen(), a.getDescripcion(),
                    a.getEstado() != null ? a.getEstado().name() : ""
            });
        }

        modeloDistribuciones.setRowCount(0);
        for (DistribucionesEntity d : t.getDistribuciones()) {
            modeloDistribuciones.addRow(new Object[]{
                    d.getIdDistribucion(),
                    d.getPuntoVenta() != null ? d.getPuntoVenta().getIdPunto() : "",
                    fmtH(d.getFechaEnvio()), d.getCantidadEnviada(), d.getEstado()
            });
        }
    }

    private String fmt(java.time.LocalDate valor) {
        return valor != null ? valor.format(fecha) : "";
    }

    private String fmtH(java.time.LocalDateTime valor) {
        return valor != null ? valor.format(fechaHora) : "";
    }

    public JButton getBtnBuscar() { return btnBuscar; }
}
