package com.nestle.trazabilidad.service;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface OperacionSQL<T> {
    T ejecutar(Connection conexion) throws SQLException;
}
