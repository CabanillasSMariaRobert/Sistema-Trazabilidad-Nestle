package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.RolDAO;
import com.nestle.trazabilidad.entity.RolEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolDAOImpl implements RolDAO {

    @Override
    public Integer insertar(Connection conexion, RolEntity rol) throws SQLException {
        String sql = "INSERT INTO Roles (nombre, descripcion, estado) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rol.getNombre());
            ps.setString(2, rol.getDescripcion());
            ps.setString(3, rol.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<RolEntity> buscarPorId(Connection conexion, Integer idRol) throws SQLException {
        String sql = "SELECT * FROM Roles WHERE id_rol = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RolEntity r = new RolEntity();
                    r.setIdRol(rs.getInt("id_rol"));
                    r.setNombre(rs.getString("nombre"));
                    r.setDescripcion(rs.getString("descripcion"));
                    r.setEstado(rs.getString("estado"));
                    return Optional.of(r);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<RolEntity> listarTodos(Connection conexion) throws SQLException {
        List<RolEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Roles";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RolEntity r = new RolEntity();
                r.setIdRol(rs.getInt("id_rol"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));
                r.setEstado(rs.getString("estado"));
                lista.add(r);
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Connection conexion, RolEntity rol) throws SQLException {
        String sql = "UPDATE Roles SET nombre = ?, descripcion = ?, estado = ? WHERE id_rol = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, rol.getNombre());
            ps.setString(2, rol.getDescripcion());
            ps.setString(3, rol.getEstado());
            ps.setInt(4, rol.getIdRol());
            return ps.executeUpdate() > 0;
        }
    }
}
