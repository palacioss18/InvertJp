USE SistemaInversiones;
GO

-- 1. Eliminar primero las tablas dependientes (por las FK) y luego Clientes
DROP TABLE IF EXISTS Transacciones;
DROP TABLE IF EXISTS PlazosFijos;
DROP TABLE IF EXISTS Clientes;
GO

-- 2. Crear de nuevo la tabla Clientes
CREATE TABLE Clientes (
    id_cliente INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    saldo DECIMAL(18,2)
);

-- 3. Crear la tabla Transacciones
CREATE TABLE Transacciones (
    id_transaccion INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente INT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    monto DECIMAL(18,2) NOT NULL,
    fecha DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_Transacciones_Clientes FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

CREATE TABLE PlazosFijos (
    id_plazo_fijo INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente INT NOT NULL,
    monto DECIMAL(18,2) NOT NULL,
    dias INT NOT NULL,
    tna DECIMAL(5,2) NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_PlazosFijos_Clientes FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

GO

-- 1. Insertar más clientes de prueba (INSERT)
INSERT INTO Clientes (nombre, saldo) VALUES 
('Mariana', 350000.00),
('Carlos', 50000.00),
('Sofia', 1200000.00),
('Lucas', 0.00);

-- 2. Insertar transacciones variadas
INSERT INTO Transacciones (id_cliente, tipo, monto) VALUES 
(1, 'DEPOSITO', 50000.00),
(1, 'RETIRO', 10000.00),
(2, 'DEPOSITO', 50000.00),
(3, 'DEPOSITO', 200000.00),
(4, 'DEPOSITO', 350000.00);

-- 3. Cargar varios plazos fijos
INSERT INTO PlazosFijos (id_cliente, monto, dias, tna) VALUES 
(1, 30000.00, 30, 38.00),
(1, 50000.00, 60, 40.00),
(3, 100000.00, 30, 38.00),
(4, 250000.00, 90, 42.00);



--EJERCICIOS
--UPDATE Y DELETE
--1) AUMENTA UN 10% EL SALDO AL CLIENTE CON ID 1
UPDATE Clientes SET saldo = saldo * 1.10 where id_cliente = 1;

--2) ELIMINA LAS TRANSACCIONES DEL CLIENTE QUE TIENE SALDO 0
DELETE FROM Transacciones WHERE id_cliente = 7; 


--JOINS
--3)MOSTRÁ EL NOMBRE DEL CLIENTE JUNTO CON EL MONTO Y LOS DIAS DE SUS PLAZOS FIJOS
SELECT c.nombre,pf.monto,pf.dias,pf.tna FROM Clientes AS c INNER JOIN PlazosFijos AS pf ON c.id_cliente = pf.id_cliente;

--4)MOSTRÁ TODOS LOS CLIENTES,TENGAN O NO UN PLAZO FIJO CREADO
SELECT c.nombre,pf.monto,pf.dias,pf.tna FROM Clientes AS c LEFT JOIN PlazosFijos AS pf ON c.id_cliente = pf.id_cliente;


--FUNCIONES DE AGRUPACION
--1)OBTENÉ EL TOTAL DEPOSITADO POR TIPO DE TRANSACCIÓN Y CUÁNTAS OPERACIONES SE HICIERON DE CADA UNA.
SELECT tipo,
       COUNT(*) AS cantidad_operaciones,
       SUM(monto) AS total_monto,
       AVG(monto) AS promedio_monto
       FROM Transacciones GROUP BY tipo;

--2)Mostra los clientes que tengan más de 1 plazo fijo armado
SELECT id_cliente, COUNT(*) AS total_plazos_fijos
    FROM PlazosFijos GROUP BY id_cliente HAVING COUNT(*) > 1;


--CASE
--1)CLASIFICÁ A LOS CLIENTES SEGÚN SU SALDO DISPONIBLE EN CATEGORIAS DE PERFIL DE INVERSOR
SELECT nombre,saldo,
    CASE
        WHEN saldo >= 500000 THEN 'Cliente VIP / Inversor Mayorista'
        WHEN saldo >= 100000 THEN 'Inversor Moderado'
        ELSE 'Inversor inicial'
    END AS perfil_cliente
    FROM Clientes;


--SUBCONSULTAS
--1)ENCONTRA LOS CLIENTES CUYO SALDO SEA MAYOR AL PROMEDIO GENERAL DE SALDOS DE TODA LA BASE
SELECT nombre, saldo 
FROM Clientes 
WHERE saldo > (SELECT AVG(saldo) FROM Clientes);


--FUNCIONES DE Ventana
--1)rankear los plazos fijos de cada cliente de mayor a menor monto
SELECT 
    c.nombre,
    pf.monto,
    ROW_NUMBER() OVER (PARTITION BY pf.id_cliente ORDER BY pf.monto DESC) AS pos_consecutiva,
    RANK()       OVER (PARTITION BY pf.id_cliente ORDER BY pf.monto DESC) AS ranking_con_saltos,
    DENSE_RANK() OVER (PARTITION BY pf.id_cliente ORDER BY pf.monto DESC) AS ranking_sin_saltos
FROM PlazosFijos pf
INNER JOIN Clientes c ON c.id_cliente = pf.id_cliente;


--2)calcular el saldo acumulado historial transacción por transacción para un cliente
SELECT 
    id_transaccion,
    id_cliente,
    fecha,
    tipo,
    monto,
    -- Suma el monto de la transacción actual con todas las anteriores
    SUM(CASE WHEN tipo = 'DEPOSITO' THEN monto ELSE -monto END) 
        OVER (PARTITION BY id_cliente ORDER BY fecha, id_transaccion) AS saldo_acumulado
FROM Transacciones
WHERE id_cliente = 1;


--3)Podés mostrar el monto de un plazo fijo y, en la misma fila, compararlo contra el promedio de plazos fijos de todos los clientes, sin necesidad de hacer un JOIN ni subconsulta
SELECT 
    id_plazo_fijo,
    id_cliente,
    monto,
    AVG(monto) OVER () AS promedio_general_sistema,
    monto - AVG(monto) OVER () AS diferencia_con_promedio
FROM PlazosFijos;
