package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.AlertasSanitariasEntity;
import com.nestle.trazabilidad.enums.EstadosAlertas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AlertasSanitariasDAO {
    Integer insertar(Connection conexion, AlertasSanitariasEntity alerta) throws SQLException;
    Optional<AlertasSanitariasEntity> buscarPorId(Connection conexion, Integer idAlerta) throws SQLException;
    boolean existePorOrigenYComunicado(Connection conexion, String origen, String nroComunicado) throws SQLException;
    List<AlertasSanitariasEntity> listarPorEstado(Connection conexion, EstadosAlertas estado) throws SQLException;
    boolean actualizarEstado(Connection conexion, Integer idAlerta, EstadosAlertas estado) throws SQLException;
}
