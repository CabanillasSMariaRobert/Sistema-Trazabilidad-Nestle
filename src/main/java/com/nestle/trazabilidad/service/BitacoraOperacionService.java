package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.BitacorasOperacionesDAO;
import com.nestle.trazabilidad.dao.implementaciones.BitacorasOperacionesDAOImpl;
import com.nestle.trazabilidad.entity.BitacoresOperacionesEntity;
import com.nestle.trazabilidad.model.filtros.FiltroBitacorasModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BitacoraOperacionService extends ServicioBase {

    private final BitacorasOperacionesDAO dao = new BitacorasOperacionesDAOImpl();

    public Integer insertar(BitacoresOperacionesEntity operacion) throws SQLException {
        return ejecutarEnTransaccion(conn -> dao.insertar(conn, operacion));
    }

    public Optional<BitacoresOperacionesEntity> buscarPorId(Integer idOperacion) throws SQLException {
        return ejecutar(conn -> dao.buscarPorId(conn, idOperacion));
    }

    public List<BitacoresOperacionesEntity> listarConFiltros(FiltroBitacorasModel filtro, int limite, int desplazamiento) throws SQLException {
        return ejecutar(conn -> dao.listarConFiltros(conn, filtro, limite, desplazamiento));
    }
}
