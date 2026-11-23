package com.nestle.trazabilidad.dao;

import com.nestle.trazabilidad.entity.BitacoresOperacionesEntity;
import com.nestle.trazabilidad.model.filtros.FiltroBitacorasModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BitacorasOperacionesDAO {
    Integer insertar(Connection conexion, BitacoresOperacionesEntity operacion) throws SQLException;
    Optional<BitacoresOperacionesEntity> buscarPorId(Connection conexion, Integer idOperacion) throws SQLException;
    List<BitacoresOperacionesEntity> listarConFiltros(Connection conexion, FiltroBitacorasModel filtro, int limite, int desplazamiento) throws SQLException;
}