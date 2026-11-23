package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.PuntoVentaDAO;
import com.nestle.trazabilidad.entity.PuntosVentaEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PuntoVentaDAOImpl implements PuntoVentaDAO {

    @Override
    public Integer insertar(Connection conexion, PuntosVentaEntity punto) throws SQLException {
        String sql = "INSERT INTO PuntosVenta (nombre, direccion, ciudad, tipo, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, punto.getNombre());
            ps.setString(2, punto.getDireccion());
            ps.setString(3, punto.getCiudad());
            ps.setString(4, punto.getTipo());
            ps.setString(5, punto.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<PuntosVentaEntity> buscarPorId(Connection conexion, Integer idPunto) throws SQLException {
        String sql = "SELECT * FROM PuntosVenta WHERE id_punto = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPunto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PuntosVentaEntity p = new PuntosVentaEntity();
                    p.setIdPunto(rs.getInt("id_punto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDireccion(rs.getString("direccion"));
                    p.setCiudad(rs.getString("ciudad"));
                    p.setTipo(rs.getString("tipo"));
                    p.setEstado(rs.getString("estado"));
                    return Optional.of(p);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<PuntosVentaEntity> listarTodos(Connection conexion) throws SQLException {
        List<PuntosVentaEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM PuntosVenta";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PuntosVentaEntity p = new PuntosVentaEntity();
                p.setIdPunto(rs.getInt("id_punto"));
                p.setNombre(rs.getString("nombre"));
                p.setDireccion(rs.getString("direccion"));
                p.setCiudad(rs.getString("ciudad"));
                p.setTipo(rs.getString("tipo"));
                p.setEstado(rs.getString("estado"));
                lista.add(p);
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Connection conexion, PuntosVentaEntity punto) throws SQLException {
        String sql = "UPDATE PuntosVenta SET nombre = ?, direccion = ?, ciudad = ?, tipo = ?, estado = ? WHERE id_punto = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, punto.getNombre());
            ps.setString(2, punto.getDireccion());
            ps.setString(3, punto.getCiudad());
            ps.setString(4, punto.getTipo());
            ps.setString(5, punto.getEstado());
            ps.setInt(6, punto.getIdPunto());
            return ps.executeUpdate() > 0;
        }
    }
}
