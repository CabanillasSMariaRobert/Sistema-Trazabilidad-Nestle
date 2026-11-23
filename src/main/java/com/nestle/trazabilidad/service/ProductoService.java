package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.ProductoDAO;
import com.nestle.trazabilidad.dao.implementaciones.ProductoDAOImpl;
import com.nestle.trazabilidad.entity.ProductosEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductoService extends ServicioBase {

    private final ProductoDAO dao = new ProductoDAOImpl();

    public Integer insertar(ProductosEntity producto) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, producto));
    }

    public Optional<ProductosEntity> buscarPorId(Integer idProducto) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idProducto));
    }

    public List<ProductosEntity> listarTodos() throws SQLException {
        return ejecutar(dao::listarTodos);
    }

    public boolean actualizar(ProductosEntity producto) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.actualizar(conn, producto));
    }
}
