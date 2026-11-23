package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.AtencionesReclamosEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AtencionReclamoDAO {
    Integer insertar(Connection conexion, AtencionesReclamosEntity atencion) throws SQLException;
    Optional<AtencionesReclamosEntity> buscarPorId(Connection conexion, Integer idAtencion) throws SQLException;
    List<AtencionesReclamosEntity> listarTodos(Connection conexion) throws SQLException;
    List<AtencionesReclamosEntity> listarPorReclamo(Connection conexion, Integer idReclamo) throws SQLException;
}
