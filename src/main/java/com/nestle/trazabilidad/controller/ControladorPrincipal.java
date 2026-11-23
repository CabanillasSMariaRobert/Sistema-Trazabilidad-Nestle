package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.view.VentanaPrincipal;

/** Controla la navegación entre los 12 paneles de la ventana principal. */
public class ControladorPrincipal extends ControladorBase {

    public static final String PANEL_ALERTAS = "alertas";
    public static final String PANEL_LOTES = "lotes";
    public static final String PANEL_TRAZABILIDAD = "trazabilidad";
    public static final String PANEL_PRODUCTOS = "productos";
    public static final String PANEL_CONTROLES = "controles";
    public static final String PANEL_DESVIACIONES = "desviaciones";
    public static final String PANEL_DISTRIBUCIONES = "distribuciones";
    public static final String PANEL_RETIROS = "retiros";
    public static final String PANEL_RECLAMOS = "reclamos";
    public static final String PANEL_USUARIOS = "usuarios";
    public static final String PANEL_BITACORAS = "bitacoras";

    private final VentanaPrincipal vista;

    public ControladorPrincipal(VentanaPrincipal vista) {
        this.vista = vista;

        vista.getBtnAlertas().addActionListener(e -> {
            vista.mostrarPanel(PANEL_ALERTAS);
            vista.getControladorAlertas().cargarAlertas();
        });

        vista.getBtnLotes().addActionListener(e -> {
            vista.mostrarPanel(PANEL_LOTES);
            vista.getControladorLotes().cargarLotes();
        });

        vista.getBtnTrazabilidad().addActionListener(e -> {
            vista.mostrarPanel(PANEL_TRAZABILIDAD);
            vista.getControladorTrazabilidad().cargarLotes();
        });

        vista.getBtnProductos().addActionListener(e -> {
            vista.mostrarPanel(PANEL_PRODUCTOS);
            vista.getControladorProductos().cargarProductos();
        });

        vista.getBtnControles().addActionListener(e -> {
            vista.mostrarPanel(PANEL_CONTROLES);
            vista.getControladorControles().cargarControles();
        });

        vista.getBtnDesviaciones().addActionListener(e -> {
            vista.mostrarPanel(PANEL_DESVIACIONES);
            vista.getControladorDesviaciones().cargarDesviaciones();
        });

        vista.getBtnDistribuciones().addActionListener(e -> {
            vista.mostrarPanel(PANEL_DISTRIBUCIONES);
            vista.getControladorDistribuciones().cargarDistribuciones();
        });

        vista.getBtnRetiros().addActionListener(e -> {
            vista.mostrarPanel(PANEL_RETIROS);
            vista.getControladorRetiros().cargarRetiros();
        });

        vista.getBtnReclamos().addActionListener(e -> {
            vista.mostrarPanel(PANEL_RECLAMOS);
            vista.getControladorReclamos().cargarReclamos();
        });

        vista.getBtnUsuarios().addActionListener(e -> {
            vista.mostrarPanel(PANEL_USUARIOS);
            vista.getControladorUsuarios().cargarDatos();
        });

        vista.getBtnBitacoras().addActionListener(e -> {
            vista.mostrarPanel(PANEL_BITACORAS);
            vista.getControladorBitacoras().cargarBitacoras();
        });

        vista.getItemSalir().addActionListener(e -> vista.dispose());

        // Carga inicial del panel visible
        vista.getControladorAlertas().cargarAlertas();
    }
}
