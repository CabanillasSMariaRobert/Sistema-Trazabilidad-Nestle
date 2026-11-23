package com.nestle.trazabilidad.service;

import com.nestle.trazabilidad.dao.*;
import com.nestle.trazabilidad.dao.implementaciones.*;
import com.nestle.trazabilidad.entity.*;
import com.nestle.trazabilidad.model.TrazabilidadLoteModel;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de alto nivel que orquesta operaciones que abarcan varias entidades
 * dentro de una sola transacción (commit/rollback atómico).
 */
public class TrazabilidadService extends ServicioBase {

    private final AlertasSanitariasDAO alertasSanitariasDAO = new AlertasSanitariasDAOImpl();
    private final AlertasLotesDAO alertasLotesDAO = new AlertasLotesDAOImpl();
    private final LoteDAO loteDAO = new LoteDAOImpl();
    private final RetiroDAO retiroDAO = new RetiroDAOImpl();
    private final DetalleRetiroDAO detalleRetiroDAO = new DetalleRetiroDAOImpl();
    private final DistribucionDAO distribucionDAO = new DistribucionDAOImpl();
    private final ProductoDAO productoDAO = new ProductoDAOImpl();
    private final ControlCalidadDAO controlCalidadDAO = new ControlCalidadDAOImpl();
    private final DesviacionDAO desviacionDAO = new DesviacionDAOImpl();
    private final BitacorasOperacionesDAO bitacoraDAO = new BitacorasOperacionesDAOImpl();

    /**
     * Registra una alerta sanitaria y la asocia a varios lotes en una sola transacción.
     * También deja constancia en la bitácora.
     *
     * @return el id de la alerta registrada
     */
    public Integer registrarAlertaConLotes(AlertasSanitariasEntity alerta,
                                           List<Integer> idsLotes,
                                           Integer idUsuarioBitacora) throws SQLException {
        return ejecutarEnTransaccion(conn -> {
            Integer idAlerta = alertasSanitariasDAO.insertar(conn, alerta);

            if (idAlerta == null) {
                throw new SQLException("No se pudo registrar la alerta sanitaria.");
            }

            for (Integer idLote : idsLotes) {
                if (alertasLotesDAO.existeAsociacion(conn, idAlerta, idLote)) {
                    continue;
                }

                AlertasLotesEntity asociacion = new AlertasLotesEntity();
                asociacion.setAlerta(alerta);
                asociacion.setLote(referenciaLote(idLote));
                asociacion.setUsuario(referenciaUsuario(idUsuarioBitacora));
                asociacion.setFechaAsociacion(LocalDateTime.now());
                alertasLotesDAO.insertar(conn, asociacion);
            }

            registrarBitacora(conn, idUsuarioBitacora, "REGISTRAR_ALERTA",
                    "AlertasSanitarias", idAlerta.toString(), null, null);

            return idAlerta;
        });
    }

    /**
     * Inicia un retiro a partir de una alerta-lote, actualiza el estado del lote
     * a EN_RETIRO y registra la operación en bitácora.
     *
     * @return el id del retiro creado
     */
    public Integer iniciarRetiro(Integer idAlertaLote, Integer idUsuario,
                                 String observaciones) throws SQLException {
        return ejecutarEnTransaccion(conn -> {
            AlertasLotesEntity asociacion = alertasLotesDAO.buscarPorId(conn, idAlertaLote)
                    .orElseThrow(() -> new SQLException("No existe la asociación alerta-lote " + idAlertaLote));

            RetirosEntity retiro = new RetirosEntity();
            retiro.setAlertaLote(asociacion);
            retiro.setUsuario(referenciaUsuario(idUsuario));
            retiro.setFechaInicio(LocalDateTime.now());
            retiro.setEstado("ABIERTO");
            retiro.setObservaciones(observaciones);

            Integer idRetiro = retiroDAO.insertar(conn, retiro);
            if (idRetiro == null) {
                throw new SQLException("No se pudo iniciar el retiro.");
            }

            loteDAO.actualizarEstado(conn, asociacion.getLote().getIdLote(), "EN_RETIRO");

            registrarBitacora(conn, idUsuario, "INICIAR_RETIRO",
                    "Retiros", idRetiro.toString(), null, "{\"estado\":\"ABIERTO\"}");

            return idRetiro;
        });
    }

    /**
     * Registra la recuperación de producto en un punto de venta, actualiza el estado
     * de la distribución a RETIRO_PARCIAL y deja constancia en bitácora.
     *
     * @return el id del detalle de retiro creado
     */
    public Integer registrarRecuperacion(Integer idRetiro, Integer idDistribucion,
                                         Integer idUsuario, Integer cantidadRecuperada,
                                         String observaciones) throws SQLException {
        return ejecutarEnTransaccion(conn -> {
            DistribucionesEntity distribucion = distribucionDAO.buscarPorId(conn, idDistribucion)
                    .orElseThrow(() -> new SQLException("No existe la distribución " + idDistribucion));

            DetallesRetiroEntity detalle = new DetallesRetiroEntity();
            detalle.setRetiro(referenciaRetiro(idRetiro));
            detalle.setDistribucion(distribucion);
            detalle.setUsuario(referenciaUsuario(idUsuario));
            detalle.setFechaRecuperacion(LocalDateTime.now());
            detalle.setCantidadRecuperada(cantidadRecuperada);
            detalle.setObservaciones(observaciones);

            Integer idDetalle = detalleRetiroDAO.insertar(conn, detalle);
            if (idDetalle == null) {
                throw new SQLException("No se pudo registrar la recuperación.");
            }

            distribucionDAO.actualizarEstado(conn, idDistribucion, "RETIRO_PARCIAL");

            registrarBitacora(conn, idUsuario, "REGISTRAR_RECUPERACION",
                    "DetallesRetiro", idDetalle.toString(), null,
                    "{\"cantidad_recuperada\":" + cantidadRecuperada + "}");

            return idDetalle;
        });
    }

    /** Cierra un retiro y actualiza el lote a RETIRADO. */
    public boolean cerrarRetiro(Integer idRetiro, Integer idUsuario) throws SQLException {
        return ejecutarEnTransaccion(conn -> {
            RetirosEntity retiro = retiroDAO.buscarPorId(conn, idRetiro)
                    .orElseThrow(() -> new SQLException("No existe el retiro " + idRetiro));

            boolean actualizado = retiroDAO.actualizarEstado(conn, idRetiro, "CERRADO");
            if (!actualizado) {
                return false;
            }

            AlertasLotesEntity asociacion = alertasLotesDAO.buscarPorId(
                    conn, retiro.getAlertaLote().getIdAlertaLote()).orElse(null);
            if (asociacion != null && asociacion.getLote() != null) {
                loteDAO.actualizarEstado(conn, asociacion.getLote().getIdLote(), "RETIRADO");
            }

            registrarBitacora(conn, idUsuario, "CERRAR_RETIRO",
                    "Retiros", idRetiro.toString(), "{\"estado\":\"ABIERTO\"}", "{\"estado\":\"CERRADO\"}");

            return true;
        });
    }

    /** Asocia un lote existente a una alerta ya registrada, validando duplicados y registrando en bitácora. */
    public Integer asociarLoteAAlerta(Integer idAlerta, Integer idLote, Integer idUsuario) throws SQLException {
        return ejecutarEnTransaccion(conn -> {
            AlertasSanitariasEntity alerta = alertasSanitariasDAO.buscarPorId(conn, idAlerta)
                    .orElseThrow(() -> new SQLException("No existe la alerta " + idAlerta));

            if (alertasLotesDAO.existeAsociacion(conn, idAlerta, idLote)) {
                throw new SQLException("El lote " + idLote + " ya está asociado a la alerta " + idAlerta);
            }

            AlertasLotesEntity asociacion = new AlertasLotesEntity();
            asociacion.setAlerta(alerta);
            asociacion.setLote(referenciaLote(idLote));
            asociacion.setUsuario(referenciaUsuario(idUsuario));
            asociacion.setFechaAsociacion(LocalDateTime.now());

            Integer idAsociacion = alertasLotesDAO.insertar(conn, asociacion);
            if (idAsociacion == null) {
                throw new SQLException("No se pudo asociar el lote a la alerta.");
            }

            registrarBitacora(conn, idUsuario, "ASOCIAR_LOTE_ALERTA",
                    "AlertasLotes", idAsociacion.toString(), null,
                    "{\"id_alerta\":" + idAlerta + ",\"id_lote\":" + idLote + "}");

            return idAsociacion;
        });
    }

    /** Devuelve el historial completo de un lote (CU-03). */
    public TrazabilidadLoteModel consultarTrazabilidad(Integer idLote) throws SQLException {
        return ejecutar(conn -> {
            LotesEntity lote = loteDAO.buscarPorId(conn, idLote)
                    .orElseThrow(() -> new SQLException("No existe el lote " + idLote));

            ProductosEntity producto = lote.getProducto() != null
                    ? productoDAO.buscarPorId(conn, lote.getProducto().getIdProducto()).orElse(null)
                    : null;

            List<AlertasSanitariasEntity> alertas = new ArrayList<>();
            for (AlertasLotesEntity vinculo : alertasLotesDAO.listarPorLote(conn, idLote)) {
                if (vinculo.getAlerta() != null) {
                    alertasSanitariasDAO.buscarPorId(conn, vinculo.getAlerta().getIdAlerta())
                            .ifPresent(alertas::add);
                }
            }

            return TrazabilidadLoteModel.builder()
                    .lote(lote)
                    .producto(producto)
                    .controles(controlCalidadDAO.listarPorLote(conn, idLote))
                    .desviaciones(desviacionDAO.listarPorLote(conn, idLote))
                    .alertas(alertas)
                    .distribuciones(distribucionDAO.listarPorLote(conn, idLote))
                    .build();
        });
    }

    /** Registra una fila en la bitácora reutilizando la conexión de la transacción actual. */
    private void registrarBitacora(java.sql.Connection conn, Integer idUsuario, String accion,
                                   String entidad, String idRegistro,
                                   String valoresAnteriores, String valoresNuevos) throws SQLException {
        BitacoresOperacionesEntity bitacora = new BitacoresOperacionesEntity();
        bitacora.setUsuario(referenciaUsuario(idUsuario));
        bitacora.setFechaHora(LocalDateTime.now());
        bitacora.setAccion(accion);
        bitacora.setEntidadAfectada(entidad);
        bitacora.setIdentificadorRegistro(idRegistro);
        bitacora.setValoresAnteriores(valoresAnteriores);
        bitacora.setValoresNuevos(valoresNuevos);
        bitacoraDAO.insertar(conn, bitacora);
    }

    private UsuarioEntity referenciaUsuario(Integer idUsuario) {
        UsuarioEntity u = new UsuarioEntity();
        u.setIdUsuario(idUsuario);
        return u;
    }

    private LotesEntity referenciaLote(Integer idLote) {
        LotesEntity l = new LotesEntity();
        l.setIdLote(idLote);
        return l;
    }

    private RetirosEntity referenciaRetiro(Integer idRetiro) {
        RetirosEntity r = new RetirosEntity();
        r.setIdRetiro(idRetiro);
        return r;
    }
}
