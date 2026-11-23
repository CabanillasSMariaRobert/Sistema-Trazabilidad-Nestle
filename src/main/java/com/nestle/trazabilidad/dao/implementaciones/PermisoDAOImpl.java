package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.PermisoDAO;
import com.nestle.trazabilidad.entity.PermisoEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermisoDAOImpl implements PermisoDAO {

    @Override
    public Integer insertar(Connection conexion, PermisoEntity permiso) throws SQLException {
        String sql = "INSERT INTO Permisos (codigo, nombre, descripcion) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, permiso.getCodigo());
            ps.setString(2, permiso.getNombre());
            ps.setString(3, permiso.getDescripcion());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<PermisoEntity> buscarPorId(Connection conexion, Integer idPermiso) throws SQLException {
        String sql = "SELECT * FROM Permisos WHERE id_permiso = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPermiso);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PermisoEntity p = new PermisoEntity();
                    p.setIdPermiso(rs.getInt("id_permiso"));
                    p.setCodigo(rs.getString("codigo"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    return Optional.of(p);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<PermisoEntity> listarTodos(Connection conexion) throws SQLException {
        List<PermisoEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Permisos";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PermisoEntity p = new PermisoEntity();
                p.setIdPermiso(rs.getInt("id_permiso"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                lista.add(p);
            }
        }
        return lista;
    }
}
