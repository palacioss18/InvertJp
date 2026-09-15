

USE SistemaInversiones;
GO

-- 1. Eliminar tablas en orden correcto de dependencias (de hijas a padre)
DROP TABLE IF EXISTS Transacciones;
DROP TABLE IF EXISTS PlazosFijos;
DROP TABLE IF EXISTS Clientes;
GO

-- 2. Tabla Clientes
CREATE TABLE Clientes (
    id_cliente INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    saldo DECIMAL(18,2)
);

-- Insertar datos de prueba en Clientes
INSERT INTO Clientes (nombre, saldo) VALUES 
    ('jose', 200.00),
    ('daniel', 100.00),
    ('palacios', 90.00);

-- 3. Tabla PlazosFijos
CREATE TABLE PlazosFijos (
    id_plazo_fijo INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente INT NOT NULL,
    monto DECIMAL(18,2) NOT NULL,
    dias INT NOT NULL,
    tna DECIMAL(5,2) NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_PlazosFijos_Clientes FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

-- 4. Tabla Transacciones
CREATE TABLE Transacciones (
    id_transaccion INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente INT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    monto DECIMAL(18,2) NOT NULL,
    fecha DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_Transacciones_Clientes FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

-- Insertar datos de prueba en Transacciones
INSERT INTO Transacciones (id_cliente, tipo, monto) VALUES 
    (1, 'DEPOSITO', 200.00),
    (2, 'DEPOSITO', 100.00),
    (3, 'DEPOSITO', 90.00);