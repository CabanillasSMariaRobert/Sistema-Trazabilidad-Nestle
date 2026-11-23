package com.nestle.trazabilidad.view;

import com.nestle.trazabilidad.controller.ControladorAlertas;
import com.nestle.trazabilidad.controller.ControladorBitacoras;
import com.nestle.trazabilidad.controller.ControladorControlesCalidad;
import com.nestle.trazabilidad.controller.ControladorDesviaciones;
import com.nestle.trazabilidad.controller.ControladorDistribuciones;
import com.nestle.trazabilidad.controller.ControladorLotes;
import com.nestle.trazabilidad.controller.ControladorPrincipal;
import com.nestle.trazabilidad.controller.ControladorProductos;
import com.nestle.trazabilidad.controller.ControladorReclamos;
import com.nestle.trazabilidad.controller.ControladorRetiros;
import com.nestle.trazabilidad.controller.ControladorTrazabilidad;
import com.nestle.trazabilidad.controller.ControladorUsuarios;
import com.nestle.trazabilidad.view.paneles.PanelAlertas;
import com.nestle.trazabilidad.view.paneles.PanelBitacoras;
import com.nestle.trazabilidad.view.paneles.PanelControlesCalidad;
import com.nestle.trazabilidad.view.paneles.PanelDesviaciones;
import com.nestle.trazabilidad.view.paneles.PanelDistribuciones;
import com.nestle.trazabilidad.view.paneles.PanelLotes;
import com.nestle.trazabilidad.view.paneles.PanelProductos;
import com.nestle.trazabilidad.view.paneles.PanelReclamos;
import com.nestle.trazabilidad.view.paneles.PanelRetiros;
import com.nestle.trazabilidad.view.paneles.PanelTrazabilidad;
import com.nestle.trazabilidad.view.paneles.PanelUsuarios;

import javax.swing.*;
import java.awt.*;

/** Ventana principal: contiene la navegación y los 12 paneles de vista. */
public class VentanaPrincipal extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contenedor = new JPanel(cardLayout);

    private final JButton btnAlertas = new JButton("Alertas Sanitarias");
    private final JButton btnLotes = new JButton("Lotes");
    private final JButton btnTrazabilidad = new JButton("Trazabilidad");
    private final JButton btnProductos = new JButton("Productos");
    private final JButton btnControles = new JButton("Control Calidad");
    private final JButton btnDesviaciones = new JButton("Desviaciones");
    private final JButton btnDistribuciones = new JButton("Distribuciones");
    private final JButton btnRetiros = new JButton("Retiros");
    private final JButton btnReclamos = new JButton("Reclamos");
    private final JButton btnUsuarios = new JButton("Usuarios y Roles");
    private final JButton btnBitacoras = new JButton("Bitácora");

    private final JMenuItem itemSalir = new JMenuItem("Salir");

    private final ControladorAlertas controladorAlertas;
    private final ControladorLotes controladorLotes;
    private final ControladorTrazabilidad controladorTrazabilidad;
    private final ControladorProductos controladorProductos;
    private final ControladorControlesCalidad controladorControles;
    private final ControladorDesviaciones controladorDesviaciones;
    private final ControladorDistribuciones controladorDistribuciones;
    private final ControladorRetiros controladorRetiros;
    private final ControladorReclamos controladorReclamos;
    private final ControladorUsuarios controladorUsuarios;
    private final ControladorBitacoras controladorBitacoras;

    public VentanaPrincipal() {
        setTitle("Sistema de Trazabilidad - Nestlé");
        setSize(1280, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ---- Paneles y controllers ----
        PanelAlertas panelAlertas = new PanelAlertas();
        PanelLotes panelLotes = new PanelLotes();
        PanelTrazabilidad panelTrazabilidad = new PanelTrazabilidad();
        PanelProductos panelProductos = new PanelProductos();
        PanelControlesCalidad panelControles = new PanelControlesCalidad();
        PanelDesviaciones panelDesviaciones = new PanelDesviaciones();
        PanelDistribuciones panelDistribuciones = new PanelDistribuciones();
        PanelRetiros panelRetiros = new PanelRetiros();
        PanelReclamos panelReclamos = new PanelReclamos();
        PanelUsuarios panelUsuarios = new PanelUsuarios();
        PanelBitacoras panelBitacoras = new PanelBitacoras();

        controladorAlertas = new ControladorAlertas(panelAlertas);
        controladorLotes = new ControladorLotes(panelLotes);
        controladorTrazabilidad = new ControladorTrazabilidad(panelTrazabilidad);
        controladorProductos = new ControladorProductos(panelProductos);
        controladorControles = new ControladorControlesCalidad(panelControles);
        controladorDesviaciones = new ControladorDesviaciones(panelDesviaciones);
        controladorDistribuciones = new ControladorDistribuciones(panelDistribuciones);
        controladorRetiros = new ControladorRetiros(panelRetiros);
        controladorReclamos = new ControladorReclamos(panelReclamos);
        controladorUsuarios = new ControladorUsuarios(panelUsuarios);
        controladorBitacoras = new ControladorBitacoras(panelBitacoras);

        contenedor.add(panelAlertas, ControladorPrincipal.PANEL_ALERTAS);
        contenedor.add(panelLotes, ControladorPrincipal.PANEL_LOTES);
        contenedor.add(panelTrazabilidad, ControladorPrincipal.PANEL_TRAZABILIDAD);
        contenedor.add(panelProductos, ControladorPrincipal.PANEL_PRODUCTOS);
        contenedor.add(panelControles, ControladorPrincipal.PANEL_CONTROLES);
        contenedor.add(panelDesviaciones, ControladorPrincipal.PANEL_DESVIACIONES);
        contenedor.add(panelDistribuciones, ControladorPrincipal.PANEL_DISTRIBUCIONES);
        contenedor.add(panelRetiros, ControladorPrincipal.PANEL_RETIROS);
        contenedor.add(panelReclamos, ControladorPrincipal.PANEL_RECLAMOS);
        contenedor.add(panelUsuarios, ControladorPrincipal.PANEL_USUARIOS);
        contenedor.add(panelBitacoras, ControladorPrincipal.PANEL_BITACORAS);

        // ---- Barra de navegación ----
        JPanel navegacion = new JPanel(new GridLayout(0, 6, 4, 4));
        navegacion.setBorder(BorderFactory.createEtchedBorder());
        navegacion.add(btnAlertas);
        navegacion.add(btnLotes);
        navegacion.add(btnTrazabilidad);
        navegacion.add(btnProductos);
        navegacion.add(btnControles);
        navegacion.add(btnDesviaciones);
        navegacion.add(btnDistribuciones);
        navegacion.add(btnRetiros);
        navegacion.add(btnReclamos);
        navegacion.add(btnUsuarios);
        navegacion.add(btnBitacoras);

        // ---- Menú ----
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());
        add(navegacion, BorderLayout.NORTH);
        add(contenedor, BorderLayout.CENTER);
    }

    public void mostrarPanel(String nombre) {
        cardLayout.show(contenedor, nombre);
    }

    public JButton getBtnAlertas() { return btnAlertas; }
    public JButton getBtnLotes() { return btnLotes; }
    public JButton getBtnTrazabilidad() { return btnTrazabilidad; }
    public JButton getBtnProductos() { return btnProductos; }
    public JButton getBtnControles() { return btnControles; }
    public JButton getBtnDesviaciones() { return btnDesviaciones; }
    public JButton getBtnDistribuciones() { return btnDistribuciones; }
    public JButton getBtnRetiros() { return btnRetiros; }
    public JButton getBtnReclamos() { return btnReclamos; }
    public JButton getBtnUsuarios() { return btnUsuarios; }
    public JButton getBtnBitacoras() { return btnBitacoras; }
    public JMenuItem getItemSalir() { return itemSalir; }

    public ControladorAlertas getControladorAlertas() { return controladorAlertas; }
    public ControladorLotes getControladorLotes() { return controladorLotes; }
    public ControladorTrazabilidad getControladorTrazabilidad() { return controladorTrazabilidad; }
    public ControladorProductos getControladorProductos() { return controladorProductos; }
    public ControladorControlesCalidad getControladorControles() { return controladorControles; }
    public ControladorDesviaciones getControladorDesviaciones() { return controladorDesviaciones; }
    public ControladorDistribuciones getControladorDistribuciones() { return controladorDistribuciones; }
    public ControladorRetiros getControladorRetiros() { return controladorRetiros; }
    public ControladorReclamos getControladorReclamos() { return controladorReclamos; }
    public ControladorUsuarios getControladorUsuarios() { return controladorUsuarios; }
    public ControladorBitacoras getControladorBitacoras() { return controladorBitacoras; }
}
