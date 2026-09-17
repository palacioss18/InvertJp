

USE SistemaInversiones;
DROP TABLE IF EXISTS dbo.Transacciones;
DROP TABLE IF EXISTS dbo.PlazosFijos;
DROP TABLE IF EXISTS dbo.Clientes;
GO

CREATE TABLE Clientes (
	cliente_id INT IDENTITY(1,1) PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL UNIQUE,
	saldo INT NOT NULL DEFAULT 0.00 CHECK (saldo >= 0), --Evita que un cliente tenga saldo negativo
	perfil_riesgo VARCHAR(50) NOT NULL DEFAULT 'CONSERVADOR'
		CHECK (perfil_riesgo IN ('CONSERVADOR','MODERADO','AGRESIVO')),
	fecha_registro DATETIME DEFAULT GETDATE() --Guarda la fecha y hora exacta del servidor en el momento de crear el registro
);


CREATE TABLE TiposInversion(
	tipo_id INT IDENTITY(1,1) PRIMARY KEY,
	codigo VARCHAR(20) NOT NULL UNIQUE, -- 'PLAZO FIJO' ,'ACCION' ,'FCI'
	descripcion VARCHAR(50) NOT NULL
);

GO

CREATE TABLE Transacciones(
	inversion_id INT IDENTITY (1,1) PRIMARY KEY,
	cliente_id INT NOT NULL,
	tipo_id INT NOT NULL,
	monto_invertido DECIMAL(18,2) NOT NULL CHECK (monto_invertido > 0),
	dias INT NOT NULL CHECK (dias > 0),
	fecha_inicio DATETIME DEFAULT GETDATE(),

	CONSTRAINT FK_Inversiones_Clientes FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id),
	CONSTRAINT Fk_Inversiones_Tipos FOREIGN KEY (tipo_id) REFERENCES TiposInversion(tipo_id)
);