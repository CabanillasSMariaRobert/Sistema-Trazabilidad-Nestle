package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.PermisoEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PermisoDAO {
    Integer insertar(Connection conexion, PermisoEntity permiso) throws SQLException;
    Optional<PermisoEntity> buscarPorId(Connection conexion, Integer idPermiso) throws SQLException;
    List<PermisoEntity> listarTodos(Connection conexion) throws SQLException;
}
