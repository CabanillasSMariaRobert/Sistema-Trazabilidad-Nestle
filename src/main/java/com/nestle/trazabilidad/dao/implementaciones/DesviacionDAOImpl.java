package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.DesviacionDAO;
import com.nestle.trazabilidad.entity.ControlesCalidadEntity;
import com.nestle.trazabilidad.entity.DesviacionesEntity;
import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DesviacionDAOImpl implements DesviacionDAO {

    @Override
    public Integer insertar(Connection conexion, DesviacionesEntity desviacion) throws SQLException {
        String sql = "INSERT INTO Desviaciones (id_lote, id_control, id_usuario, fecha_deteccion, fuente, descripcion, severidad, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, desviacion.getLote().getIdLote());
            if (desviacion.getControl() != null && desviacion.getControl().getIdControl() != null) {
                ps.setInt(2, desviacion.getControl().getIdControl());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, desviacion.getUsuario().getIdUsuario());
            ps.setObject(4, desviacion.getFechaDeteccion());
            ps.setString(5, desviacion.getFuente());
            ps.setString(6, desviacion.getDescripcion());
            ps.setString(7, desviacion.getSeveridad());
            ps.setString(8, desviacion.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<DesviacionesEntity> buscarPorId(Connection conexion, Integer idDesviacion) throws SQLException {
        String sql = "SELECT * FROM Desviaciones WHERE id_desviacion = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idDesviacion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DesviacionesEntity d = new DesviacionesEntity();
                    d.setIdDesviacion(rs.getInt("id_desviacion"));
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    d.setLote(l);
                    int idControl = rs.getInt("id_control");
                    if (!rs.wasNull()) {
                        ControlesCalidadEntity c = new ControlesCalidadEntity();
                        c.setIdControl(idControl);
                        d.setControl(c);
                    }
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    d.setUsuario(u);
                    d.setFechaDeteccion(rs.getObject("fecha_deteccion", LocalDateTime.class));
                    d.setFuente(rs.getString("fuente"));
                    d.setDescripcion(rs.getString("descripcion"));
                    d.setSeveridad(rs.getString("severidad"));
                    d.setEstado(rs.getString("estado"));
                    return Optional.of(d);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<DesviacionesEntity> listarTodos(Connection conexion) throws SQLException {
        List<DesviacionesEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Desviaciones ORDER BY id_desviacion";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DesviacionesEntity d = new DesviacionesEntity();
                d.setIdDesviacion(rs.getInt("id_desviacion"));
                LotesEntity l = new LotesEntity();
                l.setIdLote(rs.getInt("id_lote"));
                d.setLote(l);
                int idControl = rs.getInt("id_control");
                if (!rs.wasNull()) {
                    ControlesCalidadEntity c = new ControlesCalidadEntity();
                    c.setIdControl(idControl);
                    d.setControl(c);
                }
                UsuarioEntity u = new UsuarioEntity();
                u.setIdUsuario(rs.getInt("id_usuario"));
                d.setUsuario(u);
                d.setFechaDeteccion(rs.getObject("fecha_deteccion", LocalDateTime.class));
                d.setFuente(rs.getString("fuente"));
                d.setDescripcion(rs.getString("descripcion"));
                d.setSeveridad(rs.getString("severidad"));
                d.setEstado(rs.getString("estado"));
                lista.add(d);
            }
        }
        return lista;
    }

    @Override
    public List<DesviacionesEntity> listarPorLote(Connection conexion, Integer idLote) throws SQLException {
        List<DesviacionesEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Desviaciones WHERE id_lote = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idLote);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DesviacionesEntity d = new DesviacionesEntity();
                    d.setIdDesviacion(rs.getInt("id_desviacion"));
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    d.setLote(l);
                    int idControl = rs.getInt("id_control");
                    if (!rs.wasNull()) {
                        ControlesCalidadEntity c = new ControlesCalidadEntity();
                        c.setIdControl(idControl);
                        d.setControl(c);
                    }
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    d.setUsuario(u);
                    d.setFechaDeteccion(rs.getObject("fecha_deteccion", LocalDateTime.class));
                    d.setFuente(rs.getString("fuente"));
                    d.setDescripcion(rs.getString("descripcion"));
                    d.setSeveridad(rs.getString("severidad"));
                    d.setEstado(rs.getString("estado"));
                    lista.add(d);
                }
            }
        }
        return lista;
    }

    @Override
    public boolean actualizarEstado(Connection conexion, Integer idDesviacion, String estado) throws SQLException {
        String sql = "UPDATE Desviaciones SET estado = ? WHERE id_desviacion = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idDesviacion);
            return ps.executeUpdate() > 0;
        }
    }
}
