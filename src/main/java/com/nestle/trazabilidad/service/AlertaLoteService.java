package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.AlertasLotesDAO;
import com.nestle.trazabilidad.dao.implementaciones.AlertasLotesDAOImpl;
import com.nestle.trazabilidad.entity.AlertasLotesEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AlertaLoteService extends ServicioBase {

    private final AlertasLotesDAO dao = new AlertasLotesDAOImpl();

    public Integer insertar(AlertasLotesEntity asociacion) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, asociacion));
    }

    public Optional<AlertasLotesEntity> buscarPorId(Integer idAlertaLote) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idAlertaLote));
    }

    public List<AlertasLotesEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public boolean existeAsociacion(Integer idAlerta, Integer idLote) throws SQLException {
        return ejecutar(conn -> dao.existeAsociacion(conn, idAlerta, idLote));
    }

    public List<AlertasLotesEntity> listarPorAlerta(Integer idAlerta) throws SQLException {
        return ejecutar(conn -> dao.listarPorAlerta(conn, idAlerta));
    }

    public List<AlertasLotesEntity> listarPorLote(Integer idLote) throws SQLException {
        return ejecutar(conn -> dao.listarPorLote(conn, idLote));
    }
}
