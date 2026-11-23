package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.RetirosEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RetiroDAO {
    Integer insertar(Connection conexion, RetirosEntity retiro) throws SQLException;
    Optional<RetirosEntity> buscarPorId(Connection conexion, Integer idRetiro) throws SQLException;
    List<RetirosEntity> listarTodos(Connection conexion) throws SQLException;
    boolean actualizarEstado(Connection conexion, Integer idRetiro, String estado) throws SQLException;
}
