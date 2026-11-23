package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.RolPermisoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolPermisoDAOImpl implements RolPermisoDAO {

    @Override
    public List<Integer> listarIdsPermisosPorRol(Connection conexion, Integer idRol) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_permiso FROM RolesPermisos WHERE id_rol = ? ORDER BY id_permiso";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) ids.add(rs.getInt("id_permiso"));
            }
        }
        return ids;
    }

    @Override
    public boolean reemplazarPermisos(Connection conexion, Integer idRol, List<Integer> idsPermisos) throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement("DELETE FROM RolesPermisos WHERE id_rol = ?")) {
            ps.setInt(1, idRol);
            ps.executeUpdate();
        }

        String sql = "INSERT IGNORE INTO RolesPermisos (id_rol, id_permiso) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (Integer idPermiso : idsPermisos) {
                ps.setInt(1, idRol);
                ps.setInt(2, idPermiso);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        return true;
    }
}
