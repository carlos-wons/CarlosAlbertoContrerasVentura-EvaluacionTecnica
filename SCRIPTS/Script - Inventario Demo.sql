-- *******************************************************
-- Esquema: inventario_demo
-- Ejecuta en MySQL 5.7+ / 8.0
-- *******************************************************

CREATE DATABASE IF NOT EXISTS inventario_demo
  DEFAULT CHARACTER SET = utf8mb4
  DEFAULT COLLATE = utf8mb4_unicode_ci;
USE inventario_demo;

-- Roles
CREATE TABLE IF NOT EXISTS roles (
  idRol TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE,
  descripcion VARCHAR(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Usuarios 
CREATE TABLE IF NOT EXISTS usuarios (
  idUsuario INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  correo VARCHAR(100) NOT NULL UNIQUE,
  contraseña VARCHAR(255) NOT NULL, -- almacenar hash, no texto plano
  idRol TINYINT UNSIGNED NOT NULL,
  estatus TINYINT(1) NOT NULL DEFAULT 1, -- 1 = activo, 0 = inactivo
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (idRol) REFERENCES roles(idRol)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Productos
CREATE TABLE IF NOT EXISTS productos (
  idProducto INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  codigo VARCHAR(50) DEFAULT NULL, -- opcional: código o SKU
  nombre VARCHAR(150) NOT NULL,
  descripcion TEXT DEFAULT NULL,
  precio DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  cantidad INT NOT NULL DEFAULT 0, -- stock actual
  estatus TINYINT(1) NOT NULL DEFAULT 1, -- 1 = activo, 0 = dado de baja
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Histórico de movimientos (entradas / salidas)
CREATE TABLE IF NOT EXISTS historico_movimientos (
  idHistorico BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  idProducto INT UNSIGNED NOT NULL,
  idUsuario INT(6) UNSIGNED NOT NULL,
  tipo ENUM('ENTRADA','SALIDA') NOT NULL,
  cantidad INT NOT NULL, -- cantidad movida (positiva siempre)
  detalle VARCHAR(255) DEFAULT NULL, -- motivo, referencia
  referencia VARCHAR(50) DEFAULT NULL, -- ej. número de ticket
  fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (idProducto) REFERENCES productos(idProducto)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  INDEX (idProducto),
  INDEX (idUsuario),
  INDEX (tipo),
  INDEX (fecha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Datos iniciales de ejemplo
INSERT INTO roles (nombre, descripcion) VALUES
  ('Administrador','Permisos totales'),
  ('Almacenista','Maneja inventario y salidas');

-- ejemplo de usuario (la contraseña debe ser un hash en producción)
INSERT INTO usuarios (nombre, correo, contraseña, idRol) VALUES
  ('Carlos Ventura','carlos@example.com', '$2y$...hash...', 1),
  ('Almacen User','almacen@example.com', '$2y$...hash...', 2);

INSERT INTO productos (codigo, nombre, descripcion, precio, cantidad) VALUES
  ('SKU-001','Cinta adhesiva','Cinta 48mm x 50m', 25.00, 0),
  ('SKU-002','Papel A4','Paquete 500 hojas', 120.00, 0);

-- Trigger: antes de insertar un movimiento, valida y actualiza stock
DELIMITER $$
CREATE TRIGGER trg_historico_before_insert
BEFORE INSERT ON historico_movimientos
FOR EACH ROW
BEGIN
  -- validaciones básicas
  IF NEW.cantidad <= 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La cantidad debe ser mayor que 0';
  END IF;

  IF NEW.tipo = 'ENTRADA' THEN
    UPDATE productos
      SET cantidad = cantidad + NEW.cantidad
      WHERE idProducto = NEW.idProducto;
  ELSEIF NEW.tipo = 'SALIDA' THEN
    -- verificar stock disponible
    IF (SELECT cantidad FROM productos WHERE idProducto = NEW.idProducto) < NEW.cantidad THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Stock insuficiente para realizar la salida';
    END IF;
    UPDATE productos
      SET cantidad = cantidad - NEW.cantidad
      WHERE idProducto = NEW.idProducto;
  END IF;
END$$
DELIMITER ;

