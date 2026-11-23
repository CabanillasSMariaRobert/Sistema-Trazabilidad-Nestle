package com.nestle.trazabilidad;

import com.nestle.trazabilidad.controller.ControladorPrincipal;
import com.nestle.trazabilidad.view.VentanaPrincipal;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            new ControladorPrincipal(ventana);
            ventana.setVisible(true);
        });
    }
}
