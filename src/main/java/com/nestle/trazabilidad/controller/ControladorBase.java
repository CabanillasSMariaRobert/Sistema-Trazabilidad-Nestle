package com.nestle.trazabilidad.controller;

import javax.swing.JOptionPane;
import java.awt.Component;

/** Utilidades comunes de interacción con el usuario para todos los controllers. */
public abstract class ControladorBase {

    protected void mostrarError(Component padre, String titulo, Exception e) {
        e.printStackTrace();
        String mensaje = e.getMessage() != null ? e.getMessage() : e.toString();
        JOptionPane.showMessageDialog(padre, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }

    protected void mostrarInformacion(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    protected Integer pedirEntero(Component padre, String mensaje, String titulo) {
        String entrada = JOptionPane.showInputDialog(padre, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
        if (entrada == null || entrada.isBlank()) {
            return null;
        }
        try {
            return Integer.valueOf(entrada.trim());
        } catch (NumberFormatException e) {
            mostrarError(padre, "Entrada inválida", new IllegalArgumentException("Debe ingresar un número entero."));
            return null;
        }
    }

    protected boolean confirmar(Component padre, String mensaje) {
        return JOptionPane.showConfirmDialog(padre, mensaje, "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /** Entero de un campo de texto; devuelve null si está vacío o no es válido. */
    protected Integer enteroDe(String texto) {
        if (texto == null || texto.isBlank()) return null;
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected java.time.LocalDate fechaDe(String texto) throws IllegalArgumentException {
        if (texto == null || texto.isBlank()) return null;
        return java.time.LocalDate.parse(texto.trim());
    }

    protected java.time.LocalDateTime fechaHoraDe(String texto) throws IllegalArgumentException {
        if (texto == null || texto.isBlank()) return null;
        try {
            return java.time.LocalDateTime.parse(texto.trim());
        } catch (IllegalArgumentException e) {
            return java.time.LocalDate.parse(texto.trim()).atStartOfDay();
        }
    }
}
