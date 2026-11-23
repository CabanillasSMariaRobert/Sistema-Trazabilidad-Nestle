package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.DistribucionDAO;
import com.nestle.trazabilidad.dao.implementaciones.DistribucionDAOImpl;
import com.nestle.trazabilidad.entity.DistribucionesEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DistribucionService extends ServicioBase {

    private final DistribucionDAO dao = new DistribucionDAOImpl();

    public Integer insertar(DistribucionesEntity distribucion) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, distribucion));
    }

    public Optional<DistribucionesEntity> buscarPorId(Integer idDistribucion) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idDistribucion));
    }

    public List<DistribucionesEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public List<DistribucionesEntity> listarPorLote(Integer idLote) throws SQLException {
        return ejecutar(conn -> dao.listarPorLote(conn, idLote));
    }

    public boolean actualizarEstado(Integer idDistribucion, String estado) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.actualizarEstado(conn, idDistribucion, estado));
    }
}
