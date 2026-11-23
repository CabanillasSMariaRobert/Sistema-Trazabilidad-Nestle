package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.PuntoVentaDAO;
import com.nestle.trazabilidad.dao.implementaciones.PuntoVentaDAOImpl;
import com.nestle.trazabilidad.entity.PuntosVentaEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PuntoVentaService extends ServicioBase {

    private final PuntoVentaDAO dao = new PuntoVentaDAOImpl();

    public Integer insertar(PuntosVentaEntity punto) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, punto));
    }

    public Optional<PuntosVentaEntity> buscarPorId(Integer idPunto) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idPunto));
    }

    public List<PuntosVentaEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public boolean actualizar(PuntosVentaEntity punto) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.actualizar(conn, punto));
    }
}
