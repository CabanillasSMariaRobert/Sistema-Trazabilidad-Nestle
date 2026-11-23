package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.LoteDAO;
import com.nestle.trazabilidad.dao.implementaciones.LoteDAOImpl;
import com.nestle.trazabilidad.entity.LotesEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LoteService extends ServicioBase {

    private final LoteDAO dao = new LoteDAOImpl();

    public Integer insertar(LotesEntity lote) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, lote));
    }

    public Optional<LotesEntity> buscarPorId(Integer idLote) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idLote));
    }

    public List<LotesEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public List<LotesEntity> listarPorProducto(Integer idProducto) throws SQLException {
        return ejecutar(conn -> dao.listarPorProducto(conn, idProducto));
    }

    public boolean actualizarEstado(Integer idLote, String estado) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.actualizarEstado(conn, idLote, estado));
    }
}
