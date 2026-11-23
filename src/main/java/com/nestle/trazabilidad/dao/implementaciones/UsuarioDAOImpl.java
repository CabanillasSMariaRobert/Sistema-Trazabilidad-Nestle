package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.UsuarioDAO;
import com.nestle.trazabilidad.entity.RolEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Integer insertar(Connection conexion, UsuarioEntity usuario) throws SQLException {
        String sql = "INSERT INTO Usuarios (id_rol, nombres, apellidos, user_name, password_hash, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, usuario.getRol().getIdRol());
            ps.setString(2, usuario.getNombres());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getUserName());
            ps.setString(5, usuario.getPasswordHash());
            ps.setString(6, estadoTexto(usuario.isEstado()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<UsuarioEntity> buscarPorId(Connection conexion, Integer idUsuario) throws SQLException {
        return buscarUno(conexion, "SELECT * FROM Usuarios WHERE id_usuario = ?", idUsuario);
    }

    @Override
    public Optional<UsuarioEntity> buscarPorUserName(Connection conexion, String userName) throws SQLException {
        return buscarUno(conexion, "SELECT * FROM Usuarios WHERE user_name = ?", userName);
    }

    private Optional<UsuarioEntity> buscarUno(Connection conexion, String sql, Object parametro) throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setObject(1, parametro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapear(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<UsuarioEntity> listarTodos(Connection conexion) throws SQLException {
        List<UsuarioEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios ORDER BY id_usuario";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public boolean actualizar(Connection conexion, UsuarioEntity usuario) throws SQLException {
        String sql = "UPDATE Usuarios SET id_rol = ?, nombres = ?, apellidos = ?, user_name = ?, password_hash = ?, estado = ? WHERE id_usuario = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, usuario.getRol().getIdRol());
            ps.setString(2, usuario.getNombres());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getUserName());
            ps.setString(5, usuario.getPasswordHash());
            ps.setString(6, estadoTexto(usuario.isEstado()));
            ps.setInt(7, usuario.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean cambiarEstado(Connection conexion, Integer idUsuario, boolean activo) throws SQLException {
        String sql = "UPDATE Usuarios SET estado = ? WHERE id_usuario = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estadoTexto(activo));
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    private UsuarioEntity mapear(ResultSet rs) throws SQLException {
        UsuarioEntity u = new UsuarioEntity();
        u.setIdUsuario(rs.getInt("id_usuario"));

        RolEntity rol = new RolEntity();
        rol.setIdRol(rs.getInt("id_rol"));
        u.setRol(rol);

        u.setNombres(rs.getString("nombres"));
        u.setApellidos(rs.getString("apellidos"));
        u.setUserName(rs.getString("user_name"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setEstado("ACTIVO".equalsIgnoreCase(rs.getString("estado")));
        return u;
    }

    private String estadoTexto(boolean activo) {
        return activo ? "ACTIVO" : "INACTIVO";
    }
}
