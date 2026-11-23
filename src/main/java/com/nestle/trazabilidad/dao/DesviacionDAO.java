package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.DesviacionesEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DesviacionDAO {
    Integer insertar(Connection conexion, DesviacionesEntity desviacion) throws SQLException;
    Optional<DesviacionesEntity> buscarPorId(Connection conexion, Integer idDesviacion) throws SQLException;
    List<DesviacionesEntity> listarTodos(Connection conexion) throws SQLException;
    List<DesviacionesEntity> listarPorLote(Connection conexion, Integer idLote) throws SQLException;
    boolean actualizarEstado(Connection conexion, Integer idDesviacion, String estado) throws SQLException;
}
