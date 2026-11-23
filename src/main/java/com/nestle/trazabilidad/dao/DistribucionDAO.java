package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.DistribucionesEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DistribucionDAO {
    Integer insertar(Connection conexion, DistribucionesEntity distribucion) throws SQLException;
    Optional<DistribucionesEntity> buscarPorId(Connection conexion, Integer idDistribucion) throws SQLException;
    List<DistribucionesEntity> listarTodos(Connection conexion) throws SQLException;
    List<DistribucionesEntity> listarPorLote(Connection conexion, Integer idLote) throws SQLException;
    boolean actualizarEstado(Connection conexion, Integer idDistribucion, String estado) throws SQLException;
}
