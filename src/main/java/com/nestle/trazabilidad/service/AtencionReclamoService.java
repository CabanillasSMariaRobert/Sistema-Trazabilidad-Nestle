package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.AtencionReclamoDAO;
import com.nestle.trazabilidad.dao.implementaciones.AtencionReclamoDAOImpl;
import com.nestle.trazabilidad.entity.AtencionesReclamosEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AtencionReclamoService extends ServicioBase {

    private final AtencionReclamoDAO dao = new AtencionReclamoDAOImpl();

    public Integer insertar(AtencionesReclamosEntity atencion) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, atencion));
    }

    public Optional<AtencionesReclamosEntity> buscarPorId(Integer idAtencion) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idAtencion));
    }

    public List<AtencionesReclamosEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public List<AtencionesReclamosEntity> listarPorReclamo(Integer idReclamo) throws SQLException {
        return ejecutar(conn -> dao.listarPorReclamo(conn, idReclamo));
    }
}
