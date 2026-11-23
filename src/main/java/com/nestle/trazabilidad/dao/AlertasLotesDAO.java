package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.AlertasLotesEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AlertasLotesDAO {
    Integer insertar(Connection conexion, AlertasLotesEntity asociacion) throws SQLException;
    Optional<AlertasLotesEntity> buscarPorId(Connection conexion, Integer idAlertaLote) throws SQLException;
    List<AlertasLotesEntity> listarTodos(Connection conexion) throws SQLException;
    boolean existeAsociacion(Connection conexion, Integer idAlerta, Integer idLote) throws SQLException;
    List<AlertasLotesEntity> listarPorAlerta(Connection conexion, Integer idAlerta) throws SQLException;
    List<AlertasLotesEntity> listarPorLote(Connection conexion, Integer idLote) throws SQLException;
}
