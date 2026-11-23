package com.nestle.trazabilidad.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/** Utilidades para construir formularios y tablas de los paneles. */
public final class VistaUtil {

    private VistaUtil() {}

    public static void agregarCampo(JPanel panel, GridBagConstraints gbc,
                                    int fila, int columna, String etiqueta, JComponent campo) {
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = columna + 1;
        panel.add(campo, gbc);
    }

    public static DefaultTableModel modeloTabla(String[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static JPanel panelBotones(JButton... botones) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (JButton b : botones) panel.add(b);
        return panel;
    }

    /** Devuelve el id que va al inicio de un combo con formato "id - descripción". */
    public static Integer idDeCombo(Object valor) {
        if (valor == null) return null;
        String texto = valor.toString();
        int pos = texto.indexOf(" - ");
        if (pos <= 0) return null;
        try {
            return Integer.valueOf(texto.substring(0, pos).trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
