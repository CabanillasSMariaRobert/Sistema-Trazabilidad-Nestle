package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.ControlCalidadDAO;
import com.nestle.trazabilidad.dao.implementaciones.ControlCalidadDAOImpl;
import com.nestle.trazabilidad.entity.ControlesCalidadEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ControlCalidadService extends ServicioBase {

    private final ControlCalidadDAO dao = new ControlCalidadDAOImpl();

    public Integer insertar(ControlesCalidadEntity control) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, control));
    }

    public Optional<ControlesCalidadEntity> buscarPorId(Integer idControl) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idControl));
    }

    public List<ControlesCalidadEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public List<ControlesCalidadEntity> listarPorLote(Integer idLote) throws SQLException {
        return ejecutar(conn -> dao.listarPorLote(conn, idLote));
    }
}
