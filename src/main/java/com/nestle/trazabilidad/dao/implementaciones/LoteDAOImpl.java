package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.LoteDAO;
import com.nestle.trazabilidad.entity.LotesEntity;
import com.nestle.trazabilidad.entity.ProductosEntity;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoteDAOImpl implements LoteDAO {

    @Override
    public Integer insertar(Connection conexion, LotesEntity lote) throws SQLException {
        String sql = "INSERT INTO Lotes (id_producto, codigo_lote, fecha_fabricacion, fecha_vencimiento, planta, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, lote.getProducto().getIdProducto());
            ps.setString(2, lote.getCodigoLote());
            ps.setObject(3, lote.getFechaFabricacion());
            ps.setObject(4, lote.getFechaVencimiento());
            ps.setString(5, lote.getPlanta());
            ps.setString(6, lote.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<LotesEntity> buscarPorId(Connection conexion, Integer idLote) throws SQLException {
        String sql = "SELECT * FROM Lotes WHERE id_lote = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idLote);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    ProductosEntity p = new ProductosEntity();
                    p.setIdProducto(rs.getInt("id_producto"));
                    l.setProducto(p);
                    l.setCodigoLote(rs.getString("codigo_lote"));
                    l.setFechaFabricacion(rs.getObject("fecha_fabricacion", LocalDate.class));
                    l.setFechaVencimiento(rs.getObject("fecha_vencimiento", LocalDate.class));
                    l.setPlanta(rs.getString("planta"));
                    l.setEstado(rs.getString("estado"));
                    return Optional.of(l);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<LotesEntity> listarTodos(Connection conexion) throws SQLException {
        List<LotesEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Lotes";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LotesEntity l = new LotesEntity();
                l.setIdLote(rs.getInt("id_lote"));
                ProductosEntity p = new ProductosEntity();
                p.setIdProducto(rs.getInt("id_producto"));
                l.setProducto(p);
                l.setCodigoLote(rs.getString("codigo_lote"));
                l.setFechaFabricacion(rs.getObject("fecha_fabricacion", LocalDate.class));
                l.setFechaVencimiento(rs.getObject("fecha_vencimiento", LocalDate.class));
                l.setPlanta(rs.getString("planta"));
                l.setEstado(rs.getString("estado"));
                lista.add(l);
            }
        }
        return lista;
    }

    @Override
    public List<LotesEntity> listarPorProducto(Connection conexion, Integer idProducto) throws SQLException {
        List<LotesEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Lotes WHERE id_producto = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LotesEntity l = new LotesEntity();
                    l.setIdLote(rs.getInt("id_lote"));
                    ProductosEntity p = new ProductosEntity();
                    p.setIdProducto(rs.getInt("id_producto"));
                    l.setProducto(p);
                    l.setCodigoLote(rs.getString("codigo_lote"));
                    l.setFechaFabricacion(rs.getObject("fecha_fabricacion", LocalDate.class));
                    l.setFechaVencimiento(rs.getObject("fecha_vencimiento", LocalDate.class));
                    l.setPlanta(rs.getString("planta"));
                    l.setEstado(rs.getString("estado"));
                    lista.add(l);
                }
            }
        }
        return lista;
    }

    @Override
    public boolean actualizarEstado(Connection conexion, Integer idLote, String estado) throws SQLException {
        String sql = "UPDATE Lotes SET estado = ? WHERE id_lote = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idLote);
            return ps.executeUpdate() > 0;
        }
    }
}
