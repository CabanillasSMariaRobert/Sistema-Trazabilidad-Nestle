package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.AlertasSanitariasDAO;
import com.nestle.trazabilidad.entity.AlertasSanitariasEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;
import com.nestle.trazabilidad.enums.EstadosAlertas;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlertasSanitariasDAOImpl implements AlertasSanitariasDAO {

    @Override
    public Integer insertar(Connection conexion, AlertasSanitariasEntity alerta) throws SQLException {
        String sql = "INSERT INTO AlertasSanitarias (id_usuario, nro_comunicado, fecha_emision, fecha_registro, origen, descripcion, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, alerta.getUsuario().getIdUsuario());
            ps.setString(2, alerta.getNroComunicado());
            ps.setObject(3, alerta.getFechaEmision());
            ps.setObject(4, alerta.getFechaRegistro());
            ps.setString(5, alerta.getOrigen());
            ps.setString(6, alerta.getDescripcion());
            ps.setString(7, alerta.getEstado().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<AlertasSanitariasEntity> buscarPorId(Connection conexion, Integer idAlerta) throws SQLException {
        String sql = "SELECT * FROM AlertasSanitarias WHERE id_alerta = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idAlerta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AlertasSanitariasEntity a = new AlertasSanitariasEntity();
                    a.setIdAlerta(rs.getInt("id_alerta"));
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    a.setUsuario(u);
                    a.setNroComunicado(rs.getString("nro_comunicado"));
                    a.setFechaEmision(rs.getObject("fecha_emision", LocalDate.class));
                    a.setFechaRegistro(rs.getObject("fecha_registro", LocalDateTime.class));
                    a.setOrigen(rs.getString("origen"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setEstado(EstadosAlertas.valueOf(rs.getString("estado")));
                    return Optional.of(a);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existePorOrigenYComunicado(Connection conexion, String origen, String nroComunicado) throws SQLException {
        String sql = "SELECT COUNT(*) FROM AlertasSanitarias WHERE origen = ? AND nro_comunicado = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, origen);
            ps.setString(2, nroComunicado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    @Override
    public List<AlertasSanitariasEntity> listarPorEstado(Connection conexion, EstadosAlertas estado) throws SQLException {
        List<AlertasSanitariasEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM AlertasSanitarias WHERE estado = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estado.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AlertasSanitariasEntity a = new AlertasSanitariasEntity();
                    a.setIdAlerta(rs.getInt("id_alerta"));
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    a.setUsuario(u);
                    a.setNroComunicado(rs.getString("nro_comunicado"));
                    a.setFechaEmision(rs.getObject("fecha_emision", LocalDate.class));
                    a.setFechaRegistro(rs.getObject("fecha_registro", LocalDateTime.class));
                    a.setOrigen(rs.getString("origen"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setEstado(EstadosAlertas.valueOf(rs.getString("estado")));
                    lista.add(a);
                }
            }
        }
        return lista;
    }

    @Override
    public boolean actualizarEstado(Connection conexion, Integer idAlerta, EstadosAlertas estado) throws SQLException {
        String sql = "UPDATE AlertasSanitarias SET estado = ? WHERE id_alerta = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estado.name());
            ps.setInt(2, idAlerta);
            return ps.executeUpdate() > 0;
        }
    }
}
