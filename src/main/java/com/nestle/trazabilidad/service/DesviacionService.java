package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.DesviacionDAO;
import com.nestle.trazabilidad.dao.implementaciones.DesviacionDAOImpl;
import com.nestle.trazabilidad.entity.DesviacionesEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DesviacionService extends ServicioBase {

    private final DesviacionDAO dao = new DesviacionDAOImpl();

    public Integer insertar(DesviacionesEntity desviacion) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, desviacion));
    }

    public Optional<DesviacionesEntity> buscarPorId(Integer idDesviacion) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idDesviacion));
    }

    public List<DesviacionesEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public List<DesviacionesEntity> listarPorLote(Integer idLote) throws SQLException {
        return ejecutar(conn -> dao.listarPorLote(conn, idLote));
    }

    public boolean actualizarEstado(Integer idDesviacion, String estado) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.actualizarEstado(conn, idDesviacion, estado));
    }
}
