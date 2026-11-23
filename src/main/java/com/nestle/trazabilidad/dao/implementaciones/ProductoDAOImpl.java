package com.nestle.trazabilidad.dao.implementaciones;

import com.nestle.trazabilidad.dao.ProductoDAO;
import com.nestle.trazabilidad.entity.ProductosEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public Integer insertar(Connection conexion, ProductosEntity producto) throws SQLException {
        String sql = "INSERT INTO Productos (codigo, nombre, descripcion, categoria, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setString(4, producto.getCategoria());
            ps.setString(5, producto.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public Optional<ProductosEntity> buscarPorId(Connection conexion, Integer idProducto) throws SQLException {
        String sql = "SELECT * FROM Productos WHERE id_producto = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ProductosEntity p = new ProductosEntity();
                    p.setIdProducto(rs.getInt("id_producto"));
                    p.setCodigo(rs.getString("codigo"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setEstado(rs.getString("estado"));
                    return Optional.of(p);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ProductosEntity> listarTodos(Connection conexion) throws SQLException {
        List<ProductosEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM Productos";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductosEntity p = new ProductosEntity();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setCategoria(rs.getString("categoria"));
                p.setEstado(rs.getString("estado"));
                lista.add(p);
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Connection conexion, ProductosEntity producto) throws SQLException {
        String sql = "UPDATE Productos SET codigo = ?, nombre = ?, descripcion = ?, categoria = ?, estado = ? WHERE id_producto = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setString(4, producto.getCategoria());
            ps.setString(5, producto.getEstado());
            ps.setInt(6, producto.getIdProducto());
            return ps.executeUpdate() > 0;
        }
    }
}
