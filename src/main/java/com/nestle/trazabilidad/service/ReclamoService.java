package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.ReclamoDAO;
import com.nestle.trazabilidad.dao.implementaciones.ReclamoDAOImpl;
import com.nestle.trazabilidad.entity.ReclamosEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReclamoService extends ServicioBase {

    private final ReclamoDAO dao = new ReclamoDAOImpl();

    public Integer insertar(ReclamosEntity reclamo) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, reclamo));
    }

    public Optional<ReclamosEntity> buscarPorId(Integer idReclamo) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idReclamo));
    }

    public List<ReclamosEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public boolean actualizarEstado(Integer idReclamo, String estado) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.actualizarEstado(conn, idReclamo, estado));
    }
}
