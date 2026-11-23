package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.UsuarioDAO;
import com.nestle.trazabilidad.dao.implementaciones.UsuarioDAOImpl;
import com.nestle.trazabilidad.entity.UsuarioEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioService extends ServicioBase {

    private final UsuarioDAO dao = new UsuarioDAOImpl();

    public Integer insertar(UsuarioEntity usuario) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, usuario));
    }

    public Optional<UsuarioEntity> buscarPorId(Integer idUsuario) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idUsuario));
    }

    public Optional<UsuarioEntity> buscarPorUserName(String userName) throws SQLException {
        return ejecutar(conn -> dao.buscarPorUserName(conn, userName));
    }

    public List<UsuarioEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public boolean actualizar(UsuarioEntity usuario) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.actualizar(conn, usuario));
    }

    public boolean cambiarEstado(Integer idUsuario, boolean activo) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.cambiarEstado(conn, idUsuario, activo));
    }
}
