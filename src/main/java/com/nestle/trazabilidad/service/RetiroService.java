package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.RetiroDAO;
import com.nestle.trazabilidad.dao.implementaciones.RetiroDAOImpl;
import com.nestle.trazabilidad.entity.RetirosEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RetiroService extends ServicioBase {

    private final RetiroDAO dao = new RetiroDAOImpl();

    public Integer insertar(RetirosEntity retiro) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, retiro));
    }

    public Optional<RetirosEntity> buscarPorId(Integer idRetiro) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idRetiro));
    }

    public List<RetirosEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public boolean actualizarEstado(Integer idRetiro, String estado) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.actualizarEstado(conn, idRetiro, estado));
    }
}
