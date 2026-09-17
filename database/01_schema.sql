USE SistemaInversiones;
GO

-- Limpieza preventiva
DROP TABLE IF EXISTS dbo.PlazosFijos;
DROP TABLE IF EXISTS dbo.Acciones;
DROP TABLE IF EXISTS dbo.FondosComunesInversion;
DROP TABLE IF EXISTS dbo.Transacciones;
DROP TABLE IF EXISTS dbo.TiposInversion;
DROP TABLE IF EXISTS dbo.Clientes;
GO

-- 1. Tabla Clientes
CREATE TABLE Clientes (
    cliente_id INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    saldo DECIMAL(18,2) NOT NULL DEFAULT 0.00 CHECK (saldo >= 0),
    perfil_riesgo VARCHAR(50) NOT NULL DEFAULT 'CONSERVADOR'
        CHECK (perfil_riesgo IN ('CONSERVADOR','MODERADO','AGRESIVO')),
    fecha_registro DATETIME DEFAULT GETDATE()
);
GO

-- 2. Catálogo de Tipos
CREATE TABLE TiposInversion (
    tipo_id INT IDENTITY(1,1) PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE, -- 'PLAZO_FIJO', 'ACCION', 'FCI'
    descripcion VARCHAR(50) NOT NULL
);
GO

-- 3. Tabla Padre: Transacciones
CREATE TABLE Transacciones (
    inversion_id INT IDENTITY(1,1) PRIMARY KEY,
    cliente_id INT NOT NULL,
    tipo_id INT NOT NULL,
    monto_invertido DECIMAL(18,2) NOT NULL CHECK (monto_invertido > 0),
    dias INT NOT NULL CHECK (dias > 0),
    fecha_inicio DATETIME DEFAULT GETDATE(),

    CONSTRAINT FK_Transacciones_Clientes FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id),
    CONSTRAINT FK_Transacciones_Tipos FOREIGN KEY (tipo_id) REFERENCES TiposInversion(tipo_id)
);
GO

-- 4. Tabla Hija 1: Plazos Fijos
CREATE TABLE PlazosFijos (
    inversion_id INT PRIMARY KEY,
    tna DECIMAL(5,2) NOT NULL CHECK (tna > 0),
    
    CONSTRAINT FK_PlazosFijos_Transacciones FOREIGN KEY (inversion_id) 
        REFERENCES Transacciones(inversion_id) ON DELETE CASCADE
);
GO

-- 5. Tabla Hija 2: Acciones
CREATE TABLE Acciones (
    inversion_id INT PRIMARY KEY,
    ticker VARCHAR(10) NOT NULL,
    cantidad DECIMAL(18,4) NOT NULL CHECK (cantidad > 0),
    precio_compra DECIMAL(18,2) NOT NULL CHECK (precio_compra > 0),
    precio_actual DECIMAL(18,2) NOT NULL CHECK (precio_actual >= 0),
    
    CONSTRAINT FK_Acciones_Transacciones FOREIGN KEY (inversion_id) 
        REFERENCES Transacciones(inversion_id) ON DELETE CASCADE
);
GO

-- 6. Tabla Hija 3: FCI
CREATE TABLE FondosComunesInversion (
    inversion_id INT PRIMARY KEY,
    nombre_fondo VARCHAR(100) NOT NULL,
    valor_cuotaparte_inicial DECIMAL(18,4) NOT NULL CHECK (valor_cuotaparte_inicial > 0),
    valor_cuotaparte_actual DECIMAL(18,4) NOT NULL CHECK (valor_cuotaparte_actual >= 0),
    
    CONSTRAINT FK_FCI_Transacciones FOREIGN KEY (inversion_id) 
        REFERENCES Transacciones(inversion_id) ON DELETE CASCADE
);
GO

-- Cargar catálogo de tipos
INSERT INTO TiposInversion (codigo, descripcion) VALUES 
('PLAZO_FIJO', 'Plazo Fijo Tradicional'),
('ACCION', 'Acciones / Renta Variable'),
('FCI', 'Fondo Común de Inversión');

-- Cargar Cliente
INSERT INTO Clientes (nombre, email, saldo, perfil_riesgo) VALUES 
('Jose Palacios', 'jose@email.com', 500000.00, 'MODERADO');
GO

-- Inserciones seguras usando Variables para capturar el ID
BEGIN TRANSACTION;

DECLARE @id_pf INT, @id_accion INT, @id_fci INT;

-- 1. Insertar Plazo Fijo
INSERT INTO Transacciones (cliente_id, tipo_id, monto_invertido, dias) 
VALUES (1, 1, 100000.00, 30);
SET @id_pf = SCOPE_IDENTITY();

INSERT INTO PlazosFijos (inversion_id, tna) 
VALUES (@id_pf, 70.00);

-- 2. Insertar Acción
INSERT INTO Transacciones (cliente_id, tipo_id, monto_invertido, dias) 
VALUES (1, 2, 50000.00, 60);
SET @id_accion = SCOPE_IDENTITY();

INSERT INTO Acciones (inversion_id, ticker, cantidad, precio_compra, precio_actual) 
VALUES (@id_accion, 'YPF', 10.0, 5000.00, 6200.00);

-- 3. Insertar FCI
INSERT INTO Transacciones (cliente_id, tipo_id, monto_invertido, dias) 
VALUES (1, 3, 30000.00, 30);
SET @id_fci = SCOPE_IDENTITY();

INSERT INTO FondosComunesInversion (inversion_id, nombre_fondo, valor_cuotaparte_inicial, valor_cuotaparte_actual) 
VALUES (@id_fci, 'Fondo Balanceado AR', 100.00, 115.00);

COMMIT TRANSACTION;
GO


BEGIN TRY
    BEGIN TRANSACTION;

    UPDATE Clientes SET saldo = saldo - 100000.00 WHERE cliente_id = 1;

    INSERT INTO Transacciones(cliente_id,tipo_id,monto_invertido,dias) VALUES (1,1,100000.00,30);

    INSERT INTO PlazosFijos (inversion_id,tna) VALUES (SCOPE_IDENTITY(),70.00);

    COMMIT TRANSACTION;
END TRY
BEGIN CATCH
    ROLLBACK TRANSACTION;
    PRINT 'Error al registrar la inversion: ' + ERROR_MESSAGE();
END CATCH;