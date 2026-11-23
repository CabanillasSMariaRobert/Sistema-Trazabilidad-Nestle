package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.UsuarioEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    Integer insertar(Connection conexion, UsuarioEntity usuario) throws SQLException;
    Optional<UsuarioEntity> buscarPorId(Connection conexion, Integer idUsuario) throws SQLException;
    Optional<UsuarioEntity> buscarPorUserName(Connection conexion, String userName) throws SQLException;
    List<UsuarioEntity> listarTodos(Connection conexion) throws SQLException;
    boolean actualizar(Connection conexion, UsuarioEntity usuario) throws SQLException;
    boolean cambiarEstado(Connection conexion, Integer idUsuario, boolean activo) throws SQLException;
}
