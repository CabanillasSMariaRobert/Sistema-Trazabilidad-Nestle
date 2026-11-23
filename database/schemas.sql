-- ============================================================
-- SISTEMA DE TRAZABILIDAD NESTLÉ
-- Esquema de base de datos (MySQL 8+)
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ------------------------------------------------------------
-- Roles y permisos
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS Roles (
    id_rol      INT AUTO_INCREMENT,
    nombre      VARCHAR(60)  NOT NULL,
    descripcion VARCHAR(200),
    estado      ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    PRIMARY KEY (id_rol)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Permisos (
    id_permiso  INT AUTO_INCREMENT,
    codigo      VARCHAR(80)  NOT NULL,
    nombre      VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200),
    PRIMARY KEY (id_permiso),
    UNIQUE KEY uk_permisos_codigo (codigo)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS RolesPermisos (
    id_rol      INT NOT NULL,
    id_permiso  INT NOT NULL,
    PRIMARY KEY (id_rol, id_permiso),
    CONSTRAINT fk_rp_rol     FOREIGN KEY (id_rol)     REFERENCES Roles(id_rol)     ON DELETE CASCADE,
    CONSTRAINT fk_rp_permiso FOREIGN KEY (id_permiso) REFERENCES Permisos(id_permiso) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Usuarios
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS Usuarios (
    id_usuario   INT AUTO_INCREMENT,
    id_rol       INT NOT NULL,
    nombres      VARCHAR(80)  NOT NULL,
    apellidos    VARCHAR(80)  NOT NULL,
    user_name    VARCHAR(40)  NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    estado       ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    PRIMARY KEY (id_usuario),
    UNIQUE KEY uk_usuarios_username (user_name),
    CONSTRAINT fk_usuarios_rol FOREIGN KEY (id_rol) REFERENCES Roles(id_rol)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Productos y lotes
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS Productos (
    id_producto INT AUTO_INCREMENT,
    codigo      VARCHAR(30)  NOT NULL,
    nombre      VARCHAR(120) NOT NULL,
    descripcion TEXT,
    categoria   VARCHAR(60),
    estado      ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    PRIMARY KEY (id_producto),
    UNIQUE KEY uk_productos_codigo (codigo)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Lotes (
    id_lote           INT AUTO_INCREMENT,
    id_producto       INT NOT NULL,
    codigo_lote       VARCHAR(30) NOT NULL,
    fecha_fabricacion DATE,
    fecha_vencimiento DATE,
    planta            VARCHAR(100),
    estado            ENUM('PENDIENTE','LIBERADO','OBSERVADO','EN_RETIRO','RETIRADO')
                      NOT NULL DEFAULT 'PENDIENTE',
    PRIMARY KEY (id_lote),
    UNIQUE KEY uk_lotes_codigo (codigo_lote),
    CONSTRAINT fk_lotes_producto FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Control de calidad y desviaciones
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ControlesCalidad (
    id_control      INT AUTO_INCREMENT,
    id_lote         INT NOT NULL,
    id_usuario      INT NOT NULL,
    fecha_control   DATETIME NOT NULL,
    parametro       VARCHAR(100) NOT NULL,
    valor_obtenido  VARCHAR(100),
    resultado       ENUM('CONFORME','NO_CONFORME') NOT NULL,
    observaciones   TEXT,
    PRIMARY KEY (id_control),
    CONSTRAINT fk_controles_lote    FOREIGN KEY (id_lote)    REFERENCES Lotes(id_lote),
    CONSTRAINT fk_controles_usuario FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Desviaciones (
    id_desviacion   INT AUTO_INCREMENT,
    id_lote         INT NOT NULL,
    id_control      INT,
    id_usuario      INT NOT NULL,
    fecha_deteccion DATETIME NOT NULL,
    fuente          VARCHAR(100),
    descripcion     TEXT,
    severidad       ENUM('BAJA','MEDIA','ALTA','CRITICA') NOT NULL,
    estado          ENUM('ABIERTA','EN_ANALISIS','CERRADA') NOT NULL DEFAULT 'ABIERTA',
    PRIMARY KEY (id_desviacion),
    CONSTRAINT fk_desviaciones_lote    FOREIGN KEY (id_lote)    REFERENCES Lotes(id_lote),
    CONSTRAINT fk_desviaciones_control FOREIGN KEY (id_control) REFERENCES ControlesCalidad(id_control),
    CONSTRAINT fk_desviaciones_usuario FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Alertas sanitarias y su relación con lotes
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS AlertasSanitarias (
    id_alerta       INT AUTO_INCREMENT,
    id_usuario      INT NOT NULL,
    nro_comunicado  VARCHAR(80) NOT NULL,
    fecha_emision   DATE NOT NULL,
    fecha_registro  DATETIME NOT NULL,
    origen          VARCHAR(150),
    descripcion     TEXT,
    estado          ENUM('ACTIVA','CERRADA') NOT NULL DEFAULT 'ACTIVA',
    PRIMARY KEY (id_alerta),
    CONSTRAINT fk_alertas_usuario FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS AlertasLotes (
    id_alerta_lote    INT AUTO_INCREMENT,
    id_alerta         INT NOT NULL,
    id_lote           INT NOT NULL,
    id_usuario        INT NOT NULL,
    fecha_asociacion  DATETIME NOT NULL,
    PRIMARY KEY (id_alerta_lote),
    UNIQUE KEY uk_alerta_lote (id_alerta, id_lote),
    CONSTRAINT fk_al_alerta  FOREIGN KEY (id_alerta)  REFERENCES AlertasSanitarias(id_alerta),
    CONSTRAINT fk_al_lote    FOREIGN KEY (id_lote)    REFERENCES Lotes(id_lote),
    CONSTRAINT fk_al_usuario FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Puntos de venta y distribuciones
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS PuntosVenta (
    id_punto   INT AUTO_INCREMENT,
    nombre     VARCHAR(120) NOT NULL,
    direccion  VARCHAR(200),
    ciudad     VARCHAR(80),
    tipo       VARCHAR(40),
    estado     ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    PRIMARY KEY (id_punto)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Distribuciones (
    id_distribucion  INT AUTO_INCREMENT,
    id_lote          INT NOT NULL,
    id_punto         INT NOT NULL,
    id_usuario       INT NOT NULL,
    fecha_envio      DATETIME NOT NULL,
    cantidad_enviada INT NOT NULL,
    estado           ENUM('ENVIADA','EN_RETIRO','RETIRO_PARCIAL','RETIRADA')
                     NOT NULL DEFAULT 'ENVIADA',
    PRIMARY KEY (id_distribucion),
    CONSTRAINT fk_dist_lote    FOREIGN KEY (id_lote)    REFERENCES Lotes(id_lote),
    CONSTRAINT fk_dist_punto   FOREIGN KEY (id_punto)   REFERENCES PuntosVenta(id_punto),
    CONSTRAINT fk_dist_usuario FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Retiros y detalle de retiro
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS Retiros (
    id_retiro       INT AUTO_INCREMENT,
    id_alerta_lote  INT NOT NULL,
    id_usuario      INT NOT NULL,
    fecha_inicio    DATETIME NOT NULL,
    fecha_cierre    DATETIME,
    estado          ENUM('ABIERTO','EN_PROCESO','CERRADO') NOT NULL DEFAULT 'ABIERTO',
    observaciones   TEXT,
    PRIMARY KEY (id_retiro),
    CONSTRAINT fk_retiros_alerta_lote FOREIGN KEY (id_alerta_lote) REFERENCES AlertasLotes(id_alerta_lote),
    CONSTRAINT fk_retiros_usuario     FOREIGN KEY (id_usuario)     REFERENCES Usuarios(id_usuario)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS DetallesRetiro (
    id_detalle_retiro    INT AUTO_INCREMENT,
    id_retiro            INT NOT NULL,
    id_distribucion      INT NOT NULL,
    id_usuario           INT NOT NULL,
    fecha_recuperacion   DATETIME NOT NULL,
    cantidad_recuperada  INT NOT NULL,
    observaciones        TEXT,
    PRIMARY KEY (id_detalle_retiro),
    CONSTRAINT fk_dr_retiro       FOREIGN KEY (id_retiro)       REFERENCES Retiros(id_retiro),
    CONSTRAINT fk_dr_distribucion FOREIGN KEY (id_distribucion) REFERENCES Distribuciones(id_distribucion),
    CONSTRAINT fk_dr_usuario      FOREIGN KEY (id_usuario)      REFERENCES Usuarios(id_usuario)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Reclamos y atenciones
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS Reclamos (
    id_reclamo          INT AUTO_INCREMENT,
    id_producto         INT NOT NULL,
    id_lote             INT NOT NULL,
    id_usuario_registro INT NOT NULL,
    fecha_registro      DATETIME NOT NULL,
    nombre_consumidor   VARCHAR(150) NOT NULL,
    contacto            VARCHAR(150),
    motivo              VARCHAR(150) NOT NULL,
    descripcion         TEXT,
    estado              ENUM('REGISTRADO','EN_ATENCION','RESUELTO','CERRADO')
                        NOT NULL DEFAULT 'REGISTRADO',
    PRIMARY KEY (id_reclamo),
    CONSTRAINT fk_reclamos_producto   FOREIGN KEY (id_producto)         REFERENCES Productos(id_producto),
    CONSTRAINT fk_reclamos_lote       FOREIGN KEY (id_lote)             REFERENCES Lotes(id_lote),
    CONSTRAINT fk_reclamos_usuario    FOREIGN KEY (id_usuario_registro) REFERENCES Usuarios(id_usuario)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS AtencionesReclamos (
    id_atencion      INT AUTO_INCREMENT,
    id_reclamo       INT NOT NULL,
    id_usuario       INT NOT NULL,
    fecha_atencion   DATETIME NOT NULL,
    accion_realizada VARCHAR(200),
    respuesta        TEXT,
    PRIMARY KEY (id_atencion),
    CONSTRAINT fk_atenciones_reclamo FOREIGN KEY (id_reclamo)  REFERENCES Reclamos(id_reclamo),
    CONSTRAINT fk_atenciones_usuario FOREIGN KEY (id_usuario)  REFERENCES Usuarios(id_usuario)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Bitácora de operaciones
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS BitacorasOperaciones (
    id_operacion           INT AUTO_INCREMENT,
    id_usuario             INT NOT NULL,
    fecha_hora             DATETIME NOT NULL,
    accion                 VARCHAR(50) NOT NULL,
    entidad_afectada       VARCHAR(80) NOT NULL,
    identificador_registro VARCHAR(100),
    valores_anteriores     JSON,
    valores_nuevos         JSON,
    PRIMARY KEY (id_operacion),
    CONSTRAINT fk_bitacora_usuario FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;
