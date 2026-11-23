package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.BitacorasOperacionesDAO;
import com.nestle.trazabilidad.entity.BitacoresOperacionesEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;
import com.nestle.trazabilidad.model.filtros.FiltroBitacorasModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BitacorasOperacionesDAOImpl implements BitacorasOperacionesDAO {

    @Override
    public Integer insertar(Connection conexion, BitacoresOperacionesEntity operacion) throws SQLException {
        String sql = "INSERT INTO BitacorasOperaciones (id_usuario, fecha_hora, accion, entidad_afectada, identificador_registro, valores_anteriores, valores_nuevos) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, operacion.getUsuario().getIdUsuario());
            ps.setObject(2, operacion.getFechaHora());
            ps.setString(3, operacion.getAccion());
            ps.setString(4, operacion.getEntidadAfectada());
            ps.setString(5, operacion.getIdentificadorRegistro());
            ps.setString(6, operacion.getValoresAnteriores());
            ps.setString(7, operacion.getValoresNuevos());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<BitacoresOperacionesEntity> buscarPorId(Connection conexion, Integer idOperacion) throws SQLException {
        String sql = "SELECT * FROM BitacorasOperaciones WHERE id_operacion = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idOperacion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BitacoresOperacionesEntity b = new BitacoresOperacionesEntity();
                    b.setIdOperacion(rs.getInt("id_operacion"));
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    b.setUsuario(u);
                    b.setFechaHora(rs.getObject("fecha_hora", LocalDateTime.class));
                    b.setAccion(rs.getString("accion"));
                    b.setEntidadAfectada(rs.getString("entidad_afectada"));
                    b.setIdentificadorRegistro(rs.getString("identificador_registro"));
                    b.setValoresAnteriores(rs.getString("valores_anteriores"));
                    b.setValoresNuevos(rs.getString("valores_nuevos"));
                    return Optional.of(b);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<BitacoresOperacionesEntity> listarConFiltros(Connection conexion, FiltroBitacorasModel filtro, int limite, int desplazamiento) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM BitacorasOperaciones WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (filtro != null) {
            if (filtro.getIdUsuario() != null) {
                sql.append(" AND id_usuario = ?");
                params.add(filtro.getIdUsuario());
            }
            if (filtro.getDesde() != null) {
                sql.append(" AND fecha_hora >= ?");
                params.add(filtro.getDesde());
            }
            if (filtro.getHasta() != null) {
                sql.append(" AND fecha_hora <= ?");
                params.add(filtro.getHasta());
            }
            if (filtro.getAccion() != null && !filtro.getAccion().isBlank()) {
                sql.append(" AND accion = ?");
                params.add(filtro.getAccion());
            }
            if (filtro.getEntidadAfectada() != null && !filtro.getEntidadAfectada().isBlank()) {
                sql.append(" AND entidad_afectada = ?");
                params.add(filtro.getEntidadAfectada());
            }
            if (filtro.getIdentificadorRegistro() != null && !filtro.getIdentificadorRegistro().isBlank()) {
                sql.append(" AND identificador_registro = ?");
                params.add(filtro.getIdentificadorRegistro());
            }
        }

        sql.append(" ORDER BY fecha_hora DESC LIMIT ? OFFSET ?");
        params.add(limite);
        params.add(desplazamiento);

        List<BitacoresOperacionesEntity> lista = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BitacoresOperacionesEntity b = new BitacoresOperacionesEntity();
                    b.setIdOperacion(rs.getInt("id_operacion"));
                    UsuarioEntity u = new UsuarioEntity();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    b.setUsuario(u);
                    b.setFechaHora(rs.getObject("fecha_hora", LocalDateTime.class));
                    b.setAccion(rs.getString("accion"));
                    b.setEntidadAfectada(rs.getString("entidad_afectada"));
                    b.setIdentificadorRegistro(rs.getString("identificador_registro"));
                    b.setValoresAnteriores(rs.getString("valores_anteriores"));
                    b.setValoresNuevos(rs.getString("valores_nuevos"));
                    lista.add(b);
                }
            }
        }
        return lista;
    }
}
