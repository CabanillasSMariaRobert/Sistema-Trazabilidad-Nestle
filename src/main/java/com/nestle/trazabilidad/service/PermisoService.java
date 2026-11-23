package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.PermisoDAO;
import com.nestle.trazabilidad.dao.implementaciones.PermisoDAOImpl;
import com.nestle.trazabilidad.entity.PermisoEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PermisoService extends ServicioBase {

    private final PermisoDAO dao = new PermisoDAOImpl();

    public Integer insertar(PermisoEntity permiso) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, permiso));
    }

    public Optional<PermisoEntity> buscarPorId(Integer idPermiso) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idPermiso));
    }

    public List<PermisoEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }
}
