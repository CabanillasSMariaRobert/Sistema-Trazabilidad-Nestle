package com.nestle.trazabilidad.service;

/** Fábrica centralizada para obtener instancias de los servicios. */
public final class ServiceFactory {

    private static final RolService rolService = new RolService();
    private static final UsuarioService usuarioService = new UsuarioService();
    private static final PermisoService permisoService = new PermisoService();
    private static final ProductoService productoService = new ProductoService();
    private static final LoteService loteService = new LoteService();
    private static final ControlCalidadService controlCalidadService = new ControlCalidadService();
    private static final DesviacionService desviacionService = new DesviacionService();
    private static final AlertaSanitariaService alertaSanitariaService = new AlertaSanitariaService();
    private static final AlertaLoteService alertaLoteService = new AlertaLoteService();
    private static final PuntoVentaService puntoVentaService = new PuntoVentaService();
    private static final DistribucionService distribucionService = new DistribucionService();
    private static final RetiroService retiroService = new RetiroService();
    private static final DetalleRetiroService detalleRetiroService = new DetalleRetiroService();
    private static final ReclamoService reclamoService = new ReclamoService();
    private static final AtencionReclamoService atencionReclamoService = new AtencionReclamoService();
    private static final BitacoraOperacionService bitacoraOperacionService = new BitacoraOperacionService();
    private static final TrazabilidadService trazabilidadService = new TrazabilidadService();

    private ServiceFactory() {}

    public static RolService rol() { return rolService; }
    public static UsuarioService usuario() { return usuarioService; }
    public static PermisoService permiso() { return permisoService; }
    public static ProductoService producto() { return productoService; }
    public static LoteService lote() { return loteService; }
    public static ControlCalidadService controlCalidad() { return controlCalidadService; }
    public static DesviacionService desviacion() { return desviacionService; }
    public static AlertaSanitariaService alertaSanitaria() { return alertaSanitariaService; }
    public static AlertaLoteService alertaLote() { return alertaLoteService; }
    public static PuntoVentaService puntoVenta() { return puntoVentaService; }
    public static DistribucionService distribucion() { return distribucionService; }
    public static RetiroService retiro() { return retiroService; }
    public static DetalleRetiroService detalleRetiro() { return detalleRetiroService; }
    public static ReclamoService reclamo() { return reclamoService; }
    public static AtencionReclamoService atencionReclamo() { return atencionReclamoService; }
    public static BitacoraOperacionService bitacoraOperacion() { return bitacoraOperacionService; }
    public static TrazabilidadService trazabilidad() { return trazabilidadService; }
}
