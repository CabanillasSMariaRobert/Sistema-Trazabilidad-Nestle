package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.RetiroDAO;
import com.nestle.trazabilidad.entity.AlertasLotesEntity;
import com.nestle.trazabilidad.entity.RetirosEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RetiroDAOImpl implements RetiroDAO {

    @Override
    public Integer insertar(Connection conexion, RetirosEntity retiro) throws SQLException {
        String sql = "INSERT INTO Retiros (id_alerta_lote, id_usuario, fecha_inicio, fecha_cierre, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, retiro.getAlertaLote().getIdAlertaLote());
            ps.setInt(2, retiro.getUsuario().getIdUsuario());
            ps.setObject(3, retiro.getFechaInicio());
            ps.setObject(4, retiro.getFechaCierre());
            ps.setString(5, retiro.getEstado());
            ps.setString(6, retiro.getObservaciones());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<RetirosEntity> buscarPorId(Connection conexion, Integer idRetiro) throws SQLException {
        String sql = "SELECT * FROM Retiros WHERE id_retiro = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idRetiro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RetirosEntity r = new RetirosEntity();
                    r.setIdRetiro(rs.getInt("id_retiro"));
                    AlertasLotesEntity al = new AlertasLotesEntity();
                    al.setIdAlertaLote(rs.getInt("id_alerta_lote"));
                    r.setAlertaLote(al);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    r.setUsuario(u);
                    r.setFechaInicio(rs.getObject("fecha_inicio", LocalDateTime.class));
                    r.setFechaCierre(rs.getObject("fecha_cierre", LocalDateTime.class));
                    r.setEstado(rs.getString("estado"));
                    r.setObservaciones(rs.getString("observaciones"));
                    return Optional.of(r);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<RetirosEntity> listarTodos(Connection conexion) throws SQLException {
        List<RetirosEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Retiros";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RetirosEntity r = new RetirosEntity();
                r.setIdRetiro(rs.getInt("id_retiro"));
                AlertasLotesEntity al = new AlertasLotesEntity();
                al.setIdAlertaLote(rs.getInt("id_alerta_lote"));
                r.setAlertaLote(al);
                UsuarioEntity u = new UsuarioEntity();
                u.setIdUsuario(rs.getInt("id_usuario"));
                r.setUsuario(u);
                r.setFechaInicio(rs.getObject("fecha_inicio", LocalDateTime.class));
                r.setFechaCierre(rs.getObject("fecha_cierre", LocalDateTime.class));
                r.setEstado(rs.getString("estado"));
                r.setObservaciones(rs.getString("observaciones"));
                lista.add(r);
            }
        }
        return lista;
    }

    @Override
    public boolean actualizarEstado(Connection conexion, Integer idRetiro, String estado) throws SQLException {
        String sql = "UPDATE Retiros SET estado = ?, fecha_cierre = NOW() WHERE id_retiro = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idRetiro);
            return ps.executeUpdate() > 0;
        }
    }
}
