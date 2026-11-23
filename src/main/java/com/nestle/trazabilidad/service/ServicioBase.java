package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.config.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ServicioBase {

    /** Ejecuta una operación usando una conexión que se cierra al final (sin transacción explícita). */
    protected <T> T ejecutar(OperacionSQL<T> operacion) throws SQLException {
        try (Connection conexion = ConexionBD.abrir()) {
            return operacion.ejecutar(conexion);
        }
    }

    /** Ejecuta una operación dentro de una transacción: commit si todo va bien, rollback si falla. */
    protected <T> T ejecutarEnTransaccion(OperacionSQL<T> operacion) throws SQLException {
        try (Connection conexion = ConexionBD.abrir()) {
            boolean autoCommitOriginal = conexion.getAutoCommit();
            try {
                conexion.setAutoCommit(false);
                T resultado = operacion.ejecutar(conexion);
                conexion.commit();
                return resultado;
            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            } finally {
                conexion.setAutoCommit(autoCommitOriginal);
            }
        }
    }
}
