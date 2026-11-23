package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.DetalleRetiroDAO;
import com.nestle.trazabilidad.entity.DetallesRetiroEntity;
import com.nestle.trazabilidad.entity.DistribucionesEntity;
import com.nestle.trazabilidad.entity.RetirosEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DetalleRetiroDAOImpl implements DetalleRetiroDAO {

    @Override
    public Integer insertar(Connection conexion, DetallesRetiroEntity detalle) throws SQLException {
        String sql = "INSERT INTO DetallesRetiro (id_retiro, id_distribucion, id_usuario, fecha_recuperacion, cantidad_recuperada, observaciones) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, detalle.getRetiro().getIdRetiro());
            ps.setInt(2, detalle.getDistribucion().getIdDistribucion());
            ps.setInt(3, detalle.getUsuario().getIdUsuario());
            ps.setObject(4, detalle.getFechaRecuperacion());
            ps.setInt(5, detalle.getCantidadRecuperada());
            ps.setString(6, detalle.getObservaciones());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<DetallesRetiroEntity> buscarPorId(Connection conexion, Integer idDetalleRetiro) throws SQLException {
        String sql = "SELECT * FROM DetallesRetiro WHERE id_detalle_retiro = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idDetalleRetiro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DetallesRetiroEntity d = new DetallesRetiroEntity();
                    d.setIdDetalleRetiro(rs.getInt("id_detalle_retiro"));
                    RetirosEntity r = new RetirosEntity();
                    r.setIdRetiro(rs.getInt("id_retiro"));
                    d.setRetiro(r);
                    DistribucionesEntity dist = new DistribucionesEntity();
                    dist.setIdDistribucion(rs.getInt("id_distribucion"));
                    d.setDistribucion(dist);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    d.setUsuario(u);
                    d.setFechaRecuperacion(rs.getObject("fecha_recuperacion", LocalDateTime.class));
                    d.setCantidadRecuperada(rs.getInt("cantidad_recuperada"));
                    d.setObservaciones(rs.getString("observaciones"));
                    return Optional.of(d);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<DetallesRetiroEntity> listarTodos(Connection conexion) throws SQLException {
        List<DetallesRetiroEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM DetallesRetiro ORDER BY id_detalle_retiro";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DetallesRetiroEntity d = new DetallesRetiroEntity();
                d.setIdDetalleRetiro(rs.getInt("id_detalle_retiro"));
                RetirosEntity r = new RetirosEntity();
                r.setIdRetiro(rs.getInt("id_retiro"));
                d.setRetiro(r);
                DistribucionesEntity dist = new DistribucionesEntity();
                dist.setIdDistribucion(rs.getInt("id_distribucion"));
                d.setDistribucion(dist);
                UsuarioEntity u = new UsuarioEntity();
                u.setIdUsuario(rs.getInt("id_usuario"));
                d.setUsuario(u);
                d.setFechaRecuperacion(rs.getObject("fecha_recuperacion", LocalDateTime.class));
                d.setCantidadRecuperada(rs.getInt("cantidad_recuperada"));
                d.setObservaciones(rs.getString("observaciones"));
                lista.add(d);
            }
        }
        return lista;
    }

    @Override
    public List<DetallesRetiroEntity> listarPorRetiro(Connection conexion, Integer idRetiro) throws SQLException {
        List<DetallesRetiroEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM DetallesRetiro WHERE id_retiro = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idRetiro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetallesRetiroEntity d = new DetallesRetiroEntity();
                    d.setIdDetalleRetiro(rs.getInt("id_detalle_retiro"));
                    RetirosEntity r = new RetirosEntity();
                    r.setIdRetiro(rs.getInt("id_retiro"));
                    d.setRetiro(r);
                    DistribucionesEntity dist = new DistribucionesEntity();
                    dist.setIdDistribucion(rs.getInt("id_distribucion"));
                    d.setDistribucion(dist);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    d.setUsuario(u);
                    d.setFechaRecuperacion(rs.getObject("fecha_recuperacion", LocalDateTime.class));
                    d.setCantidadRecuperada(rs.getInt("cantidad_recuperada"));
                    d.setObservaciones(rs.getString("observaciones"));
                    lista.add(d);
                }
            }
        }
        return lista;
    }
}
