package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.DistribucionDAO;
import com.nestle.trazabilidad.entity.DistribucionesEntity;
import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.PuntosVentaEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DistribucionDAOImpl implements DistribucionDAO {

    @Override
    public Integer insertar(Connection conexion, DistribucionesEntity distribucion) throws SQLException {
        String sql = "INSERT INTO Distribuciones (id_lote, id_punto, id_usuario, fecha_envio, cantidad_enviada, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, distribucion.getLote().getIdLote());
            ps.setInt(2, distribucion.getPuntoVenta().getIdPunto());
            ps.setInt(3, distribucion.getUsuario().getIdUsuario());
            ps.setObject(4, distribucion.getFechaEnvio());
            ps.setInt(5, distribucion.getCantidadEnviada());
            ps.setString(6, distribucion.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<DistribucionesEntity> buscarPorId(Connection conexion, Integer idDistribucion) throws SQLException {
        String sql = "SELECT * FROM Distribuciones WHERE id_distribucion = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idDistribucion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DistribucionesEntity d = new DistribucionesEntity();
                    d.setIdDistribucion(rs.getInt("id_distribucion"));
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    d.setLote(l);
                    PuntosVentaEntity p = new PuntosVentaEntity();
                    p.setIdPunto(rs.getInt("id_punto"));
                    d.setPuntoVenta(p);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    d.setUsuario(u);
                    d.setFechaEnvio(rs.getObject("fecha_envio", LocalDateTime.class));
                    d.setCantidadEnviada(rs.getInt("cantidad_enviada"));
                    d.setEstado(rs.getString("estado"));
                    return Optional.of(d);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<DistribucionesEntity> listarTodos(Connection conexion) throws SQLException {
        List<DistribucionesEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Distribuciones ORDER BY id_distribucion";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DistribucionesEntity d = new DistribucionesEntity();
                d.setIdDistribucion(rs.getInt("id_distribucion"));
                LotesEntity l = new LotesEntity();
                l.setIdLote(rs.getInt("id_lote"));
                d.setLote(l);
                PuntosVentaEntity p = new PuntosVentaEntity();
                p.setIdPunto(rs.getInt("id_punto"));
                d.setPuntoVenta(p);
                UsuarioEntity u = new UsuarioEntity();
                u.setIdUsuario(rs.getInt("id_usuario"));
                d.setUsuario(u);
                d.setFechaEnvio(rs.getObject("fecha_envio", LocalDateTime.class));
                d.setCantidadEnviada(rs.getInt("cantidad_enviada"));
                d.setEstado(rs.getString("estado"));
                lista.add(d);
            }
        }
        return lista;
    }

    @Override
    public List<DistribucionesEntity> listarPorLote(Connection conexion, Integer idLote) throws SQLException {
        List<DistribucionesEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Distribuciones WHERE id_lote = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idLote);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DistribucionesEntity d = new DistribucionesEntity();
                    d.setIdDistribucion(rs.getInt("id_distribucion"));
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    d.setLote(l);
                    PuntosVentaEntity p = new PuntosVentaEntity();
                    p.setIdPunto(rs.getInt("id_punto"));
                    d.setPuntoVenta(p);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    d.setUsuario(u);
                    d.setFechaEnvio(rs.getObject("fecha_envio", LocalDateTime.class));
                    d.setCantidadEnviada(rs.getInt("cantidad_enviada"));
                    d.setEstado(rs.getString("estado"));
                    lista.add(d);
                }
            }
        }
        return lista;
    }

    @Override
    public boolean actualizarEstado(Connection conexion, Integer idDistribucion, String estado) throws SQLException {
        String sql = "UPDATE Distribuciones SET estado = ? WHERE id_distribucion = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idDistribucion);
            return ps.executeUpdate() > 0;
        }
    }
}
