package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.RolEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RolDAO {
    Integer insertar(Connection conexion, RolEntity rol) throws SQLException;
    Optional<RolEntity> buscarPorId(Connection conexion, Integer idRol) throws SQLException;
    List<RolEntity> listarTodos(Connection conexion) throws SQLException;
    boolean actualizar(Connection conexion, RolEntity rol) throws SQLException;
}
