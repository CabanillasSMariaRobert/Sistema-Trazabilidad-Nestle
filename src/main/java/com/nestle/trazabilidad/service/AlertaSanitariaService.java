package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.AlertasSanitariasDAO;
import com.nestle.trazabilidad.dao.implementaciones.AlertasSanitariasDAOImpl;
import com.nestle.trazabilidad.entity.AlertasSanitariasEntity;
import com.nestle.trazabilidad.enums.EstadosAlertas;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AlertaSanitariaService extends ServicioBase {

    private final AlertasSanitariasDAO dao = new AlertasSanitariasDAOImpl();

    public Integer insertar(AlertasSanitariasEntity alerta) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, alerta));
    }

    public Optional<AlertasSanitariasEntity> buscarPorId(Integer idAlerta) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idAlerta));
    }

    public boolean existePorOrigenYComunicado(String origen, String nroComunicado) throws SQLException {
        return ejecutar(conn -> dao.existePorOrigenYComunicado(conn, origen, nroComunicado));
    }

    public List<AlertasSanitariasEntity> listarPorEstado(EstadosAlertas estado) throws SQLException {
        return ejecutar(conn -> dao.listarPorEstado(conn, estado));
    }

    public boolean actualizarEstado(Integer idAlerta, EstadosAlertas estado) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.actualizarEstado(conn, idAlerta, estado));
    }
}
