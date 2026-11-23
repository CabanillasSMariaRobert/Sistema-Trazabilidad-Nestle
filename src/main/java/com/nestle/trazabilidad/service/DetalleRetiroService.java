package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.DetalleRetiroDAO;
import com.nestle.trazabilidad.dao.implementaciones.DetalleRetiroDAOImpl;
import com.nestle.trazabilidad.entity.DetallesRetiroEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DetalleRetiroService extends ServicioBase {

    private final DetalleRetiroDAO dao = new DetalleRetiroDAOImpl();

    public Integer insertar(DetallesRetiroEntity detalle) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, detalle));
    }

    public Optional<DetallesRetiroEntity> buscarPorId(Integer idDetalleRetiro) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idDetalleRetiro));
    }

    public List<DetallesRetiroEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public List<DetallesRetiroEntity> listarPorRetiro(Integer idRetiro) throws SQLException {
        return ejecutar(conn -> dao.listarPorRetiro(conn, idRetiro));
    }
}
