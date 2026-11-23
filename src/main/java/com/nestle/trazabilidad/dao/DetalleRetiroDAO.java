package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.DetallesRetiroEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DetalleRetiroDAO {
    Integer insertar(Connection conexion, DetallesRetiroEntity detalle) throws SQLException;
    Optional<DetallesRetiroEntity> buscarPorId(Connection conexion, Integer idDetalleRetiro) throws SQLException;
    List<DetallesRetiroEntity> listarTodos(Connection conexion) throws SQLException;
    List<DetallesRetiroEntity> listarPorRetiro(Connection conexion, Integer idRetiro) throws SQLException;
}
