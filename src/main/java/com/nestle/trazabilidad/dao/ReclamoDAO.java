package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.ReclamosEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ReclamoDAO {
    Integer insertar(Connection conexion, ReclamosEntity reclamo) throws SQLException;
    Optional<ReclamosEntity> buscarPorId(Connection conexion, Integer idReclamo) throws SQLException;
    List<ReclamosEntity> listarTodos(Connection conexion) throws SQLException;
    boolean actualizarEstado(Connection conexion, Integer idReclamo, String estado) throws SQLException;
}
