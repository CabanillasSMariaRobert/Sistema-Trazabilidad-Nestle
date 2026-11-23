package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.ProductosEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductoDAO {
    Integer insertar(Connection conexion, ProductosEntity producto) throws SQLException;
    Optional<ProductosEntity> buscarPorId(Connection conexion, Integer idProducto) throws SQLException;
    List<ProductosEntity> listarTodos(Connection conexion) throws SQLException;
    boolean actualizar(Connection conexion, ProductosEntity producto) throws SQLException;
}
