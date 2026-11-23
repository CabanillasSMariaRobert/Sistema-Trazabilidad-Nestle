package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.ControlesCalidadEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ControlCalidadDAO {
    Integer insertar(Connection conexion, ControlesCalidadEntity control) throws SQLException;
    Optional<ControlesCalidadEntity> buscarPorId(Connection conexion, Integer idControl) throws SQLException;
    List<ControlesCalidadEntity> listarTodos(Connection conexion) throws SQLException;
    List<ControlesCalidadEntity> listarPorLote(Connection conexion, Integer idLote) throws SQLException;
}
