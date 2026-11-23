package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.PuntosVentaEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PuntoVentaDAO {
    Integer insertar(Connection conexion, PuntosVentaEntity punto) throws SQLException;
    Optional<PuntosVentaEntity> buscarPorId(Connection conexion, Integer idPunto) throws SQLException;
    List<PuntosVentaEntity> listarTodos(Connection conexion) throws SQLException;
    boolean actualizar(Connection conexion, PuntosVentaEntity punto) throws SQLException;
}
