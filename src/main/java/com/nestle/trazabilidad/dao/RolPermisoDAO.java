package com.nestle.trazabilidad.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RolPermisoDAO {
    List<Integer> listarIdsPermisosPorRol(Connection conexion, Integer idRol) throws SQLException;
    boolean reemplazarPermisos(Connection conexion, Integer idRol, List<Integer> idsPermisos) throws SQLException;
}
