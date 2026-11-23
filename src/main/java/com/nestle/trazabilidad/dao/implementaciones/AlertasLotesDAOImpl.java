package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.AlertasLotesDAO;
import com.nestle.trazabilidad.entity.AlertasLotesEntity;
import com.nestle.trazabilidad.entity.AlertasSanitariasEntity;
import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlertasLotesDAOImpl implements AlertasLotesDAO {
    @Override
    public Integer insertar(Connection conexion, AlertasLotesEntity asociacion) throws SQLException {
        String sql = """
                INSERT INTO AlertasLotes (id_alerta, id_lote, id_usuario, fecha_asociacion)
                VALUES (?,?,?,?)
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, asociacion.getAlerta().getIdAlerta());
            ps.setInt(2, asociacion.getLote().getIdLote());
            ps.setInt(3, asociacion.getUsuario().getIdUsuario());
            ps.setObject(4, asociacion.getFechaAsociacion());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return null;
    }

    @Override
    public Optional<AlertasLotesEntity> buscarPorId(Connection conexion, Integer idAlertaLote) throws SQLException {
        String sql = "SELECT * FROM AlertasLotes WHERE id_alerta_lote = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idAlertaLote);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AlertasLotesEntity e = new AlertasLotesEntity();
                    e.setIdAlertaLote(rs.getInt("id_alerta_lote"));
                    AlertasSanitariasEntity a = new AlertasSanitariasEntity();
                    a.setIdAlerta(rs.getInt("id_alerta"));
                    e.setAlerta(a);
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    e.setLote(l);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    e.setUsuario(u);
                    e.setFechaAsociacion(rs.getObject("fecha_asociacion", LocalDateTime.class));
                    return Optional.of(e);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<AlertasLotesEntity> listarTodos(Connection conexion) throws SQLException {
        List<AlertasLotesEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM AlertasLotes ORDER BY id_alerta_lote";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AlertasLotesEntity e = new AlertasLotesEntity();
                e.setIdAlertaLote(rs.getInt("id_alerta_lote"));
                AlertasSanitariasEntity a = new AlertasSanitariasEntity();
                a.setIdAlerta(rs.getInt("id_alerta"));
                e.setAlerta(a);
                LotesEntity l = new LotesEntity();
                l.setIdLote(rs.getInt("id_lote"));
                e.setLote(l);
                UsuarioEntity u = new UsuarioEntity();
                u.setIdUsuario(rs.getInt("id_usuario"));
                e.setUsuario(u);
                e.setFechaAsociacion(rs.getObject("fecha_asociacion", LocalDateTime.class));
                lista.add(e);
            }
        }
        return lista;
    }

    @Override
    public boolean existeAsociacion(Connection conexion, Integer idAlerta, Integer idLote) throws SQLException {
        String sql = "SELECT COUNT(*) FROM AlertasLotes WHERE id_alerta = ? AND id_lote = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idAlerta);
            ps.setInt(2, idLote);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public List<AlertasLotesEntity> listarPorAlerta(Connection conexion, Integer idAlerta) throws SQLException {
        List<AlertasLotesEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM AlertasLotes WHERE id_alerta = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idAlerta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AlertasLotesEntity e = new AlertasLotesEntity();
                    e.setIdAlertaLote(rs.getInt("id_alerta_lote"));
                    AlertasSanitariasEntity a = new AlertasSanitariasEntity();
                    a.setIdAlerta(rs.getInt("id_alerta"));
                    e.setAlerta(a);
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    e.setLote(l);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    e.setUsuario(u);
                    e.setFechaAsociacion(rs.getObject("fecha_asociacion", LocalDateTime.class));
                    lista.add(e);
                }
            }
        }
        return lista;
    }

    @Override
    public List<AlertasLotesEntity> listarPorLote(Connection conexion, Integer idLote) throws SQLException {
        List<AlertasLotesEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM AlertasLotes WHERE id_lote = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idLote);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AlertasLotesEntity e = new AlertasLotesEntity();
                    e.setIdAlertaLote(rs.getInt("id_alerta_lote"));
                    AlertasSanitariasEntity a = new AlertasSanitariasEntity();
                    a.setIdAlerta(rs.getInt("id_alerta"));
                    e.setAlerta(a);
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    e.setLote(l);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    e.setUsuario(u);
                    e.setFechaAsociacion(rs.getObject("fecha_asociacion", LocalDateTime.class));
                    lista.add(e);
                }
            }
        }
        return lista;
    }
}
