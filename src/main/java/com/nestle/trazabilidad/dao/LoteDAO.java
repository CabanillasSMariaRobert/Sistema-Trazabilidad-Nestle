package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.LotesEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface LoteDAO {
    Integer insertar(Connection conexion, LotesEntity lote) throws SQLException;
    Optional<LotesEntity> buscarPorId(Connection conexion, Integer idLote) throws SQLException;
    List<LotesEntity> listarTodos(Connection conexion) throws SQLException;
    List<LotesEntity> listarPorProducto(Connection conexion, Integer idProducto) throws SQLException;
    boolean actualizarEstado(Connection conexion, Integer idLote, String estado) throws SQLException;
}
