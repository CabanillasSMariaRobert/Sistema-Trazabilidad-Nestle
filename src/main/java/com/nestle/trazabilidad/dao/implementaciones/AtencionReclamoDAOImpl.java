package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.AtencionReclamoDAO;
import com.nestle.trazabilidad.entity.AtencionesReclamosEntity;
import com.nestle.trazabilidad.entity.ReclamosEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtencionReclamoDAOImpl implements AtencionReclamoDAO {

    @Override
    public Integer insertar(Connection conexion, AtencionesReclamosEntity atencion) throws SQLException {
        String sql = "INSERT INTO AtencionesReclamos (id_reclamo, id_usuario, fecha_atencion, accion_realizada, respuesta) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, atencion.getReclamo().getIdReclamo());
            ps.setInt(2, atencion.getUsuario().getIdUsuario());
            ps.setObject(3, atencion.getFechaAtencion());
            ps.setString(4, atencion.getAccionRealizada());
            ps.setString(5, atencion.getRespuesta());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<AtencionesReclamosEntity> buscarPorId(Connection conexion, Integer idAtencion) throws SQLException {
        String sql = "SELECT * FROM AtencionesReclamos WHERE id_atencion = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idAtencion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AtencionesReclamosEntity a = new AtencionesReclamosEntity();
                    a.setIdAtencion(rs.getInt("id_atencion"));
                    ReclamosEntity r = new ReclamosEntity();
                    r.setIdReclamo(rs.getInt("id_reclamo"));
                    a.setReclamo(r);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    a.setUsuario(u);
                    a.setFechaAtencion(rs.getObject("fecha_atencion", LocalDateTime.class));
                    a.setAccionRealizada(rs.getString("accion_realizada"));
                    a.setRespuesta(rs.getString("respuesta"));
                    return Optional.of(a);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<AtencionesReclamosEntity> listarTodos(Connection conexion) throws SQLException {
        List<AtencionesReclamosEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM AtencionesReclamos ORDER BY id_atencion";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AtencionesReclamosEntity a = new AtencionesReclamosEntity();
                a.setIdAtencion(rs.getInt("id_atencion"));
                ReclamosEntity r = new ReclamosEntity();
                r.setIdReclamo(rs.getInt("id_reclamo"));
                a.setReclamo(r);
                UsuarioEntity u = new UsuarioEntity();
                u.setIdUsuario(rs.getInt("id_usuario"));
                a.setUsuario(u);
                a.setFechaAtencion(rs.getObject("fecha_atencion", LocalDateTime.class));
                a.setAccionRealizada(rs.getString("accion_realizada"));
                a.setRespuesta(rs.getString("respuesta"));
                lista.add(a);
            }
        }
        return lista;
    }

    @Override
    public List<AtencionesReclamosEntity> listarPorReclamo(Connection conexion, Integer idReclamo) throws SQLException {
        List<AtencionesReclamosEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM AtencionesReclamos WHERE id_reclamo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idReclamo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AtencionesReclamosEntity a = new AtencionesReclamosEntity();
                    a.setIdAtencion(rs.getInt("id_atencion"));
                    ReclamosEntity r = new ReclamosEntity();
                    r.setIdReclamo(rs.getInt("id_reclamo"));
                    a.setReclamo(r);
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    a.setUsuario(u);
                    a.setFechaAtencion(rs.getObject("fecha_atencion", LocalDateTime.class));
                    a.setAccionRealizada(rs.getString("accion_realizada"));
                    a.setRespuesta(rs.getString("respuesta"));
                    lista.add(a);
                }
            }
        }
        return lista;
    }
}
