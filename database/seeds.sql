-- ============================================================
-- DATOS DE PRUEBA - SISTEMA DE TRAZABILIDAD NESTLÉ
-- Caso documentado: retiro voluntario y preventivo de
-- NAN Supreme Pro 2 (Comunicado N.° 01-2026-DIGESA/MINSA, enero 2026)
-- Fuente: Avance de Proyecto Final 2 - Análisis y Diseño de Sistemas
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- Limpieza (orden inverso de dependencias)
TRUNCATE TABLE BitacorasOperaciones;
TRUNCATE TABLE AtencionesReclamos;
TRUNCATE TABLE Reclamos;
TRUNCATE TABLE DetallesRetiro;
TRUNCATE TABLE Retiros;
TRUNCATE TABLE Distribuciones;
TRUNCATE TABLE AlertasLotes;
TRUNCATE TABLE AlertasSanitarias;
TRUNCATE TABLE Desviaciones;
TRUNCATE TABLE ControlesCalidad;
TRUNCATE TABLE Lotes;
TRUNCATE TABLE Productos;
TRUNCATE TABLE PuntosVenta;
TRUNCATE TABLE RolesPermisos;
TRUNCATE TABLE Permisos;
TRUNCATE TABLE Usuarios;
TRUNCATE TABLE Roles;

-- ------------------------------------------------------------
-- 1. ROLES (actores del documento)
-- ------------------------------------------------------------
INSERT INTO Roles (id_rol, nombre, descripcion, estado) VALUES
(1, 'ADMINISTRADOR',         'Administrador del sistema: gestiona usuarios y permisos', 'ACTIVO'),
(2, 'ANALISTA_CALIDAD',      'Analista de Aseguramiento de Calidad: registra lotes, controles y alertas', 'ACTIVO'),
(3, 'SUPERVISOR_DISTRIBUCION','Supervisor de Distribución: coordina envíos y retiros en puntos de venta', 'ACTIVO'),
(4, 'AGENTE_CONSUMIDOR',     'Agente de Atención al Consumidor: registra y atiende reclamos', 'ACTIVO');

-- ------------------------------------------------------------
-- 2. PERMISOS (RF-01 .. RF-12 / CU-01 .. CU-12)
-- ------------------------------------------------------------
INSERT INTO Permisos (id_permiso, codigo, nombre, descripcion) VALUES
(1,  'CU-01', 'Registrar producto',              'RF-01 - Registrar productos con código único'),
(2,  'CU-02', 'Registrar lote',                  'RF-02 - Registrar lotes con fechas y estado'),
(3,  'CU-03', 'Consultar trazabilidad del lote', 'RF-03 - Consultar historial completo de un lote'),
(4,  'CU-04', 'Registrar control de calidad',    'RF-04 - Registrar resultados de controles'),
(5,  'CU-05', 'Registrar desviación',            'RF-05 - Registrar anomalías de calidad'),
(6,  'CU-06', 'Registrar alerta sanitaria',      'RF-06 - Registrar comunicados oficiales'),
(7,  'CU-07', 'Asociar alerta a lotes',          'RF-07 - Vincular alertas con lotes afectados'),
(8,  'CU-08', 'Registrar distribución',          'RF-08 - Registrar movimientos de distribución'),
(9,  'CU-09', 'Gestionar retiro de producto',    'RF-09 - Registrar y actualizar retiros'),
(10, 'CU-10', 'Registrar reclamo',               'RF-10 - Registrar consultas y quejas'),
(11, 'CU-11', 'Consultar alertas y atender reclamos', 'RF-11 - Atender reclamos con soporte de alertas'),
(12, 'CU-12', 'Administrar usuarios',            'RF-12 - Administrar usuarios, roles y permisos');

INSERT INTO RolesPermisos (id_rol, id_permiso) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),
(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,9),(2,11),
(3,3),(3,8),(3,9),
(4,3),(4,10),(4,11);

-- ------------------------------------------------------------
-- 3. USUARIOS (integrantes del equipo + roles del documento)
--    password_hash: SHA-256 de "123456" SOLO PARA PRUEBAS
-- ------------------------------------------------------------
INSERT INTO Usuarios (id_usuario, id_rol, nombres, apellidos, user_name, password_hash, estado) VALUES
(1, 1, 'Robert Gian',   'Cabanillas Santa Maria', 'rcabanillas', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'ACTIVO'),
(2, 2, 'Paolo',         'Anaya Palma',            'panaya',      '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'ACTIVO'),
(3, 2, 'Joao Aldhair',  'Salazar Manrique',       'jsalazar',    '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'ACTIVO'),
(4, 3, 'Carlo Joaquín', 'Buendía Díaz',           'cbuendia',    '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'ACTIVO'),
(5, 4, 'Yostin Samuel', 'Espinoza Roque',         'yespinoza',   '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'ACTIVO');

-- ------------------------------------------------------------
-- 4. PRODUCTOS (línea de nutrición infantil)
-- ------------------------------------------------------------
INSERT INTO Productos (id_producto, codigo, nombre, descripcion, categoria, estado) VALUES
(1, 'NAN-SUPRO2', 'NAN Supreme Pro 2', 'Fórmula de crecimiento 2 (1 a 3 años) - producto del comunicado DIGESA', 'Nutrición infantil', 'ACTIVO'),
(2, 'NAN-SUPRO1', 'NAN Supreme Pro 1', 'Fórmula inicial (0 a 6 meses)', 'Nutrición infantil', 'ACTIVO'),
(3, 'NAN-OPTI3',  'NAN Optipro 3',     'Fórmula de crecimiento 3 (3 a 6 años)', 'Nutrición infantil', 'ACTIVO'),
(4, 'NIDO-FORT',  'NIDO Fortificada',  'Leche en polvo fortificada', 'Nutrición infantil', 'ACTIVO'),
(5, 'PURE-MANZ',  'Nestlé Puré de Manzana', 'Puré de fruta infantil', 'Alimentos infantiles', 'ACTIVO');

-- ------------------------------------------------------------
-- 5. LOTES
--    id_lote 1 y 3 = lotes afectados por la alerta DIGESA
-- ------------------------------------------------------------
INSERT INTO Lotes (id_lote, id_producto, codigo_lote, fecha_fabricacion, fecha_vencimiento, planta, estado) VALUES
(1, 1, 'LOT-NSP2-2511A', '2025-11-03', '2027-11-03', 'Planta Lima - Los Olivos', 'RETIRADO'),
(2, 1, 'LOT-NSP2-2511B', '2025-11-10', '2027-11-10', 'Planta Lima - Los Olivos', 'LIBERADO'),
(3, 1, 'LOT-NSP2-2512A', '2025-12-01', '2027-12-01', 'Planta Ate', 'OBSERVADO'),
(4, 1, 'LOT-NSP2-2512B', '2025-12-15', '2027-12-15', 'Planta Ate', 'LIBERADO'),
(5, 1, 'LOT-NSP2-2601A', '2026-01-05', '2028-01-05', 'Planta Lima - Los Olivos', 'LIBERADO'),
(6, 2, 'LOT-NSP1-2512A', '2025-12-08', '2027-12-08', 'Planta Ate', 'LIBERADO'),
(7, 3, 'LOT-OPTI-2601A', '2026-01-10', '2028-01-10', 'Planta Lima - Los Olivos', 'PENDIENTE'),
(8, 4, 'LOT-NIDO-2512A', '2025-12-20', '2027-12-20', 'Planta Ate', 'LIBERADO');

-- ------------------------------------------------------------
-- 6. CONTROLES DE CALIDAD
--    El control 1 (lote 1) es NO_CONFORME y origina la desviación
-- ------------------------------------------------------------
INSERT INTO ControlesCalidad (id_control, id_lote, id_usuario, fecha_control, parametro, valor_obtenido, resultado, observaciones) VALUES
(1, 1, 2, '2025-12-18 09:15:00', 'Índice de humedad',        '6.8 %',  'NO_CONFORME', 'Supera el límite interno de 5.5 %. Se retiene el lote para análisis.'),
(2, 1, 2, '2025-12-18 10:40:00', 'Proteína (g/100 g)',       '14.2',   'CONFORME',    'Dentro del rango 13.5 - 15.0'),
(3, 2, 2, '2025-12-19 08:30:00', 'Índice de humedad',        '4.9 %',  'CONFORME',    'Dentro de especificación'),
(4, 3, 3, '2026-01-08 11:00:00', 'Índice de humedad',        '6.2 %',  'NO_CONFORME', 'Valor elevado; lote puesto en observación.'),
(5, 3, 3, '2026-01-08 11:45:00', 'Recuento de mesofílicos',  '1.2x10³ UFC/g', 'CONFORME', 'Dentro del límite microbiológico'),
(6, 4, 2, '2026-01-09 14:20:00', 'Índice de humedad',        '5.1 %',  'CONFORME',    'Sin observaciones'),
(7, 5, 3, '2026-01-12 16:10:00', 'Análisis sensorial',       'Aprobado', 'CONFORME', 'Panel sensorial conforme'),
(8, 6, 2, '2026-01-13 10:05:00', 'Índice de humedad',        '5.0 %',  'CONFORME',    'Sin observaciones'),
(9, 8, 2, '2026-01-14 09:00:00', 'Humedad',                  '3.8 %',  'CONFORME',    'Sin observaciones');

-- ------------------------------------------------------------
-- 7. DESVIACIONES
-- ------------------------------------------------------------
INSERT INTO Desviaciones (id_desviacion, id_lote, id_control, id_usuario, fecha_deteccion, fuente, descripcion, severidad, estado) VALUES
(1, 1, 1, 2, '2025-12-18 10:00:00', 'Control de calidad interno', 'Humedad de 6.8 % por encima del límite de 5.5 % en lote LOT-NSP2-2511A. Se inmoviliza el lote.', 'CRITICA', 'CERRADA'),
(2, 3, 4, 3, '2026-01-08 12:00:00', 'Control de calidad interno', 'Humedad de 6.2 % en lote LOT-NSP2-2512A. Se clasifica como lote observado.', 'ALTA', 'EN_ANALISIS'),
(3, 1, NULL, 2, '2026-01-15 09:45:00', 'Comunicado DIGESA/MINSA', 'Alerta externa confirma desviación de calidad en NAN Supreme Pro 2; se vincula al retiro preventivo.', 'CRITICA', 'CERRADA');

-- ------------------------------------------------------------
-- 8. ALERTAS SANITARIAS
--    Caso central: Comunicado 01-2026-DIGESA/MINSA (enero 2026)
-- ------------------------------------------------------------
INSERT INTO AlertasSanitarias (id_alerta, id_usuario, nro_comunicado, fecha_emision, fecha_registro, origen, descripcion, estado) VALUES
(1, 2, '01-2026-DIGESA/MINSA', '2026-01-15', '2026-01-15 09:30:00', 'DIGESA/MINSA', 'Digesa comunica a la población en general sobre el retiro voluntario y preventivo del mercado del producto NAN Supreme Pro 2, tras detectarse una desviación de calidad en uno de sus lotes.', 'ACTIVA'),
(2, 2, '02-2026-DIGESA/MINSA', '2026-01-22', '2026-01-22 15:10:00', 'DIGESA/MINSA', 'Comunicado complementario de seguimiento sobre el retiro preventivo de NAN Supreme Pro 2.', 'CERRADA');

-- ------------------------------------------------------------
-- 9. ALERTAS-LOTES (relación N:M, fecha de asociación)
-- ------------------------------------------------------------
INSERT INTO AlertasLotes (id_alerta_lote, id_alerta, id_lote, id_usuario, fecha_asociacion) VALUES
(1, 1, 1, 2, '2026-01-15 10:00:00'),
(2, 1, 3, 2, '2026-01-15 10:05:00'),
(3, 2, 6, 2, '2026-01-22 15:20:00');

-- ------------------------------------------------------------
-- 10. PUNTOS DE VENTA
-- ------------------------------------------------------------
INSERT INTO PuntosVenta (id_punto, nombre, direccion, ciudad, tipo, estado) VALUES
(1, 'Plaza Vea - Plaza Norte',      'Av. Túpac Amaru 6450',        'Lima',    'Supermercado', 'ACTIVO'),
(2, 'Vivanda - San Isidro',         'Av. Santa Fe 2985',           'Lima',    'Supermercado', 'ACTIVO'),
(3, 'Metro - Pueblo Libre',         'Av. Bolívar 455',             'Lima',    'Supermercado', 'ACTIVO'),
(4, 'Tottus - Parque Las Antenas',  'Av. Circunvalación 2550',     'Lima',    'Supermercado', 'ACTIVO'),
(5, 'Wong - Miraflores',            'Av. Larco 1301',              'Lima',    'Supermercado', 'ACTIVO'),
(6, 'Real Plaza - Arequipa',        'Av. Ejército 401',            'Arequipa','Centro comercial', 'ACTIVO'),
(7, 'Tottus - Trujillo',            'Av. El Golf 350',             'Trujillo','Supermercado', 'ACTIVO'),
(8, 'Tienda Nestlé - San Isidro',   'Av. República de Panamá 3290', 'Lima',   'Tienda propia', 'ACTIVO');

-- ------------------------------------------------------------
-- 11. DISTRIBUCIONES
--     Lote 1 (afectado) -> puntos 1-4 ; Lote 3 (observado) -> puntos 5-6
-- ------------------------------------------------------------
INSERT INTO Distribuciones (id_distribucion, id_lote, id_punto, id_usuario, fecha_envio, cantidad_enviada, estado) VALUES
(1,  1, 1, 4, '2025-12-22 08:00:00', 240, 'RETIRADA'),
(2,  1, 2, 4, '2025-12-23 09:30:00', 180, 'RETIRADA'),
(3,  1, 3, 4, '2025-12-24 11:15:00', 120, 'RETIRADA'),
(4,  1, 4, 4, '2026-01-05 10:00:00',  90, 'RETIRO_PARCIAL'),
(5,  3, 5, 4, '2026-01-10 08:45:00', 150, 'EN_RETIRO'),
(6,  3, 6, 4, '2026-01-11 14:00:00', 110, 'EN_RETIRO'),
(7,  2, 1, 4, '2026-01-12 07:30:00', 200, 'ENVIADA'),
(8,  2, 7, 4, '2026-01-13 12:00:00',  80, 'ENVIADA'),
(9,  5, 8, 4, '2026-01-16 09:00:00',  60, 'ENVIADA'),
(10, 8, 2, 4, '2026-01-17 10:20:00', 130, 'ENVIADA');

-- ------------------------------------------------------------
-- 12. RETIROS
-- ------------------------------------------------------------
INSERT INTO Retiros (id_retiro, id_alerta_lote, id_usuario, fecha_inicio, fecha_cierre, estado, observaciones) VALUES
(1, 1, 4, '2026-01-16 08:00:00', '2026-01-18 17:30:00', 'CERRADO', 'Retiro preventivo de LOT-NSP2-2511A en 4 puntos de venta. 435 unidades recuperadas.'),
(2, 2, 4, '2026-01-17 09:00:00', NULL, 'EN_PROCESO', 'Retiro de LOT-NSP2-2512A en curso; pendiente de recuperación en Arequipa.');

-- ------------------------------------------------------------
-- 13. DETALLES DE RETIRO (recuperación por distribución)
-- ------------------------------------------------------------
INSERT INTO DetallesRetiro (id_detalle_retiro, id_retiro, id_distribucion, id_usuario, fecha_recuperacion, cantidad_recuperada, observaciones) VALUES
(1, 1, 1, 4, '2026-01-16 10:30:00', 240, 'Recuperación total en Plaza Vea - Plaza Norte'),
(2, 1, 2, 4, '2026-01-16 12:15:00', 180, 'Recuperación total en Vivanda - San Isidro'),
(3, 1, 3, 4, '2026-01-17 09:45:00', 120, 'Recuperación total en Metro - Pueblo Libre'),
(4, 1, 4, 4, '2026-01-18 16:00:00',  75, 'Recuperación parcial en Tottus; 15 unidades ya vendidas'),
(5, 2, 5, 4, '2026-01-18 11:20:00', 140, 'Recuperación en Wong - Miraflores'),
(6, 2, 6, 4, '2026-01-19 15:10:00',  95, 'Recuperación en Real Plaza - Arequipa');

-- ------------------------------------------------------------
-- 14. RECLAMOS DE CONSUMIDORES
-- ------------------------------------------------------------
INSERT INTO Reclamos (id_reclamo, id_producto, id_lote, id_usuario_registro, fecha_registro, nombre_consumidor, contacto, motivo, descripcion, estado) VALUES
(1, 1, 1, 5, '2026-01-16 11:00:00', 'María González', 'mgrz@email.com',   'Consulta sobre alerta sanitaria', 'Consumidora solicita confirmación si el lote LOT-NSP2-2511A está comprendido en el retiro DIGESA.', 'RESUELTO'),
(2, 1, 1, 5, '2026-01-17 13:25:00', 'Jorge Ramírez',  '989123456',       'Producto aún en estantería', 'Cliente reporta que encontró NAN Supreme Pro 2 en góndola pese al comunicado de retiro.', 'EN_ATENCION'),
(3, 1, 3, 5, '2026-01-18 09:10:00', 'Lucía Mendoza',  'lmen@email.com',   'Solicitud de información', 'Pregunta por la fecha de vencimiento y el destino del lote LOT-NSP2-2512A.', 'REGISTRADO'),
(4, 2, 6, 5, '2026-01-19 16:40:00', 'Carlos Torres',  '994567890',       'Devolución de producto', 'Solicita devolución de NAN Supreme Pro 1 por duda sobre el retiro de la marca.', 'CERRADO');

-- ------------------------------------------------------------
-- 15. ATENCIONES DE RECLAMOS
-- ------------------------------------------------------------
INSERT INTO AtencionesReclamos (id_atencion, id_reclamo, id_usuario, fecha_atencion, accion_realizada, respuesta) VALUES
(1, 1, 5, '2026-01-16 12:30:00', 'Verificación de alerta en sistema', 'Se confirmó que el lote LOT-NSP2-2511A está retirado. Se recomendó no consumir el producto y acercarse al punto de venta para la devolución.'),
(2, 2, 5, '2026-01-17 15:00:00', 'Derivo a Supervisor de Distribución', 'Se informó al cliente que se realizará la verificación inmediata en el punto de venta reportado. En seguimiento.'),
(3, 4, 5, '2026-01-19 18:00:00', 'Gestión de devolución', 'Se autorizó la devolución del producto. El lote consultado no está afectado por la alerta DIGESA.');

-- ------------------------------------------------------------
-- 16. BITÁCORA DE OPERACIONES (RNF-07)
-- ------------------------------------------------------------
INSERT INTO BitacorasOperaciones (id_operacion, id_usuario, fecha_hora, accion, entidad_afectada, identificador_registro, valores_anteriores, valores_nuevos) VALUES
(1,  2, '2026-01-15 09:30:00', 'REGISTRAR_ALERTA',       'AlertasSanitarias', '1',   NULL,                                  '{"nro_comunicado":"01-2026-DIGESA/MINSA","estado":"ACTIVA"}'),
(2,  2, '2026-01-15 10:00:00', 'ASOCIAR_LOTE_ALERTA',    'AlertasLotes',      '1',   NULL,                                  '{"id_alerta":1,"id_lote":1}'),
(3,  2, '2026-01-15 10:05:00', 'ASOCIAR_LOTE_ALERTA',    'AlertasLotes',      '2',   NULL,                                  '{"id_alerta":1,"id_lote":3}'),
(4,  2, '2026-01-15 10:06:00', 'CAMBIAR_ESTADO_LOTE',    'Lotes',             '3',   '{"estado":"LIBERADO"}',                '{"estado":"OBSERVADO"}'),
(5,  4, '2026-01-16 08:00:00', 'INICIAR_RETIRO',         'Retiros',           '1',   NULL,                                  '{"estado":"ABIERTO","id_alerta_lote":1}'),
(6,  4, '2026-01-16 10:30:00', 'REGISTRAR_RECUPERACION', 'DetallesRetiro',    '1',   NULL,                                  '{"cantidad_recuperada":240,"id_distribucion":1}'),
(7,  4, '2026-01-18 17:30:00', 'CERRAR_RETIRO',          'Retiros',           '1',   '{"estado":"EN_PROCESO"}',              '{"estado":"CERRADO"}'),
(8,  2, '2026-01-18 17:31:00', 'CAMBIAR_ESTADO_LOTE',    'Lotes',             '1',   '{"estado":"OBSERVADO"}',              '{"estado":"RETIRADO"}'),
(9,  5, '2026-01-16 12:30:00', 'ATENDER_RECLAMO',        'Reclamos',          '1',   '{"estado":"REGISTRADO"}',             '{"estado":"RESUELTO"}'),
(10, 1, '2026-01-14 08:00:00', 'REGISTRAR_USUARIO',      'Usuarios',          '5',   NULL,                                  '{"user_name":"yespinoza","id_rol":4}');

SET FOREIGN_KEY_CHECKS = 1;
