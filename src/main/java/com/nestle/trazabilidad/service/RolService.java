package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.RolDAO;
import com.nestle.trazabilidad.dao.RolPermisoDAO;
import com.nestle.trazabilidad.dao.implementaciones.RolDAOImpl;
import com.nestle.trazabilidad.dao.implementaciones.RolPermisoDAOImpl;
import com.nestle.trazabilidad.entity.RolEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RolService extends ServicioBase {

    private final RolDAO dao = new RolDAOImpl();
    private final RolPermisoDAO rolPermisoDAO = new RolPermisoDAOImpl();

    public Integer insertar(RolEntity rol) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, rol));
    }

    public Optional<RolEntity> buscarPorId(Integer idRol) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idRol));
    }

    public List<RolEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public boolean actualizar(RolEntity rol) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.actualizar(conn, rol));
    }

    public List<Integer> listarPermisosPorRol(Integer idRol) throws SQLException {
        return ejecutar(conn -> rolPermisoDAO.listarIdsPermisosPorRol(conn, idRol));
    }

    public boolean asignarPermisos(Integer idRol, List<Integer> idsPermisos) throws SQLException {
        return ejecutarEnTransaccion(conn -> rolPermisoDAO.reemplazarPermisos(conn, idRol, idsPermisos));
    }
}
