package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.ReclamoDAO;
import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.ProductosEntity;
import com.nestle.trazabilidad.entity.ReclamosEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReclamoDAOImpl implements ReclamoDAO {

    @Override
    public Integer insertar(Connection conexion, ReclamosEntity reclamo) throws SQLException {
        String sql = "INSERT INTO Reclamos (id_producto, id_lote, id_usuario_registro, fecha_registro, nombre_consumidor, contacto, motivo, descripcion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, reclamo.getProducto().getIdProducto());
            ps.setInt(2, reclamo.getLote().getIdLote());
            ps.setInt(3, reclamo.getUsuarioRegistro().getIdUsuario());
            ps.setObject(4, reclamo.getFechaRegistro());
            ps.setString(5, reclamo.getNombreConsumidor());
            ps.setString(6, reclamo.getContacto());
            ps.setString(7, reclamo.getMotivo());
            ps.setString(8, reclamo.getDescripcion());
            ps.setString(9, reclamo.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<ReclamosEntity> buscarPorId(Connection conexion, Integer idReclamo) throws SQLException {
        String sql = "SELECT * FROM Reclamos WHERE id_reclamo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idReclamo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ReclamosEntity r = new ReclamosEntity();
                    r.setIdReclamo(rs.getInt("id_reclamo"));
                    ProductosEntity p = new ProductosEntity();
                    p.setIdProducto(rs.getInt("id_producto"));
                    r.setProducto(p);
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    r.setLote(l);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario_registro"));
                    r.setUsuarioRegistro(u);
                    r.setFechaRegistro(rs.getObject("fecha_registro", LocalDateTime.class));
                    r.setNombreConsumidor(rs.getString("nombre_consumidor"));
                    r.setContacto(rs.getString("contacto"));
                    r.setMotivo(rs.getString("motivo"));
                    r.setDescripcion(rs.getString("descripcion"));
                    r.setEstado(rs.getString("estado"));
                    return Optional.of(r);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ReclamosEntity> listarTodos(Connection conexion) throws SQLException {
        List<ReclamosEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Reclamos";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ReclamosEntity r = new ReclamosEntity();
                r.setIdReclamo(rs.getInt("id_reclamo"));
                ProductosEntity p = new ProductosEntity();
                p.setIdProducto(rs.getInt("id_producto"));
                r.setProducto(p);
                LotesEntity l = new LotesEntity();
                l.setIdLote(rs.getInt("id_lote"));
                r.setLote(l);
                UsuarioEntity u = new UsuarioEntity();
                u.setIdUsuario(rs.getInt("id_usuario_registro"));
                r.setUsuarioRegistro(u);
                r.setFechaRegistro(rs.getObject("fecha_registro", LocalDateTime.class));
                r.setNombreConsumidor(rs.getString("nombre_consumidor"));
                r.setContacto(rs.getString("contacto"));
                r.setMotivo(rs.getString("motivo"));
                r.setDescripcion(rs.getString("descripcion"));
                r.setEstado(rs.getString("estado"));
                lista.add(r);
            }
        }
        return lista;
    }

    @Override
    public boolean actualizarEstado(Connection conexion, Integer idReclamo, String estado) throws SQLException {
        String sql = "UPDATE Reclamos SET estado = ? WHERE id_reclamo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idReclamo);
            return ps.executeUpdate() > 0;
        }
    }
}
