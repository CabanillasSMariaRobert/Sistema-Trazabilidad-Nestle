package com.nestle.trazabilidad.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    public static Connection abrir() throws SQLException {
        String url = obtenerVariable("DB_URL");
        String usuario = obtenerVariable("DB_USER");
        String clave = obtenerVariable("DB_PASSWORD");

        return DriverManager.getConnection(url, usuario, clave);
    }

    private static String obtenerVariable(String nombre) {
        String valor = System.getenv(nombre);

        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException(
                    "Falta configurar la variable " + nombre
            );
        }

        return valor;
    }
}
