package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.ControlCalidadDAO;
import com.nestle.trazabilidad.entity.ControlesCalidadEntity;
import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControlCalidadDAOImpl implements ControlCalidadDAO {

    @Override
    public Integer insertar(Connection conexion, ControlesCalidadEntity control) throws SQLException {
        String sql = "INSERT INTO ControlesCalidad (id_lote, id_usuario, fecha_control, parametro, valor_obtenido, resultado, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, control.getLote().getIdLote());
            ps.setInt(2, control.getUsuario().getIdUsuario());
            ps.setObject(3, control.getFechaControl());
            ps.setString(4, control.getParametro());
            ps.setString(5, control.getValorObtenido());
            ps.setString(6, control.getResultado());
            ps.setString(7, control.getObservaciones());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<ControlesCalidadEntity> buscarPorId(Connection conexion, Integer idControl) throws SQLException {
        String sql = "SELECT * FROM ControlesCalidad WHERE id_control = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idControl);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ControlesCalidadEntity c = new ControlesCalidadEntity();
                    c.setIdControl(rs.getInt("id_control"));
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    c.setLote(l);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    c.setUsuario(u);
                    c.setFechaControl(rs.getObject("fecha_control", LocalDateTime.class));
                    c.setParametro(rs.getString("parametro"));
                    c.setValorObtenido(rs.getString("valor_obtenido"));
                    c.setResultado(rs.getString("resultado"));
                    c.setObservaciones(rs.getString("observaciones"));
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ControlesCalidadEntity> listarTodos(Connection conexion) throws SQLException {
        List<ControlesCalidadEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM ControlesCalidad ORDER BY id_control";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ControlesCalidadEntity c = new ControlesCalidadEntity();
                c.setIdControl(rs.getInt("id_control"));
                LotesEntity l = new LotesEntity();
                l.setIdLote(rs.getInt("id_lote"));
                c.setLote(l);
                UsuarioEntity u = new UsuarioEntity();
                u.setIdUsuario(rs.getInt("id_usuario"));
                c.setUsuario(u);
                c.setFechaControl(rs.getObject("fecha_control", LocalDateTime.class));
                c.setParametro(rs.getString("parametro"));
                c.setValorObtenido(rs.getString("valor_obtenido"));
                c.setResultado(rs.getString("resultado"));
                c.setObservaciones(rs.getString("observaciones"));
                lista.add(c);
            }
        }
        return lista;
    }

    @Override
    public List<ControlesCalidadEntity> listarPorLote(Connection conexion, Integer idLote) throws SQLException {
        List<ControlesCalidadEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM ControlesCalidad WHERE id_lote = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idLote);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ControlesCalidadEntity c = new ControlesCalidadEntity();
                    c.setIdControl(rs.getInt("id_control"));
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    c.setLote(l);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    c.setUsuario(u);
                    c.setFechaControl(rs.getObject("fecha_control", LocalDateTime.class));
                    c.setParametro(rs.getString("parametro"));
                    c.setValorObtenido(rs.getString("valor_obtenido"));
                    c.setResultado(rs.getString("resultado"));
                    c.setObservaciones(rs.getString("observaciones"));
                    lista.add(c);
                }
            }
        }
        return lista;
    }
}
