package com.nestle.trazabilidad;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Sistema de Trazabilidad");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel mensaje = new JLabel(
                "Bienvenido al sistema",
                SwingConstants.CENTER
        );

        add(mensaje);
    }
}