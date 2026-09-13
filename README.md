# 📈 Sistema de Inversiones y Rendimientos Diarios

Sistema backend desarrollado en **Java y SQL Server** diseñado para simular la gestión de inversiones financieras, colocación de plazos fijos, fondos comunes y el seguimiento de saldos y transacciones.

Este proyecto está enfocado en aplicar Programación Orientada a Objetos avanzada (Herencia y Polimorfismo),uso intensivo de **T-SQL** para analítica y lógica de base de datos.

---

## 🎯 Funcionalidades Principales

### 💰 Gestión de Plazos Fijos
* Constituir plazos fijos vinculados a clientes, registrando monto, días y Tasa Nominal Anual (TNA).
* Cálculo polimórfico de ganancias basadas en la fórmula de interés según los días estipulados.

### 📊 Simulación de Rendimientos vía Base de Datos
* Simulación de proyecciones finales e intereses a percibir mediante la ejecución de Procedimientos Almacenados (`sp_SimularPlazoFijo`) directamente en SQL Server.

### 📈 Fondos Comunes de Inversión (FCI)
* Cálculo dinámico de ganancias mediante el seguimiento del valor de cuotaparte inicial y actual.

### 🗄️ Auditoría de Transacciones y Saldos
* Registro histórico de operaciones de depósitos y retiros por cliente para control de auditoría.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 17+ (Core / SE)
* **Base de Datos:** SQL Server (T-SQL)
* **Control de Versiones:** Git & GitHub

---

## 🗄️ Conceptos Técnicos Aplicados

* **SQL Server & T-SQL (Lógica de Datos Avanzada):**
  * **Procedimientos Almacenados:** Implementación de `sp_SimularPlazoFijo` para delegar cálculos matemáticos de proyecciones a la base de datos.
  * **Funciones de Ventana (`OVER`):** Uso de `ROW_NUMBER()`, `RANK()` y `DENSE_RANK()` para rankear las inversiones de los clientes, y `SUM() OVER(...)` para calcular saldos históricos acumulados transacción por transacción.
  * **Analítica Temporal:** Aplicación de la función `LAG()` para calcular la variación económica exacta entre un plazo fijo y el inmediato anterior de un mismo cliente.

* **Java & Backend (Arquitectura y POO):**
  * **Clases Abstractas y Polimorfismo:** Creación de una entidad base `Inversion` con un método abstracto `calcularGanancia()`, permitiendo que el portafolio del cliente procese dinámicamente Plazos Fijos o FCIs en una misma colección.
  * **Encapsulamiento:** Estricto control de acceso a atributos mediante modificadores `private` y validación defensiva en métodos mutadores (Setters).
  * **Estructura Modular:** Desacoplamiento de responsabilidades dividiendo el dominio del negocio (`model`) de la ejecución principal del sistema (`app`).

---

## 📁 Estructura del Proyecto

```text
sistema-inversiones/
│
├── database/
│   ├── schema_and_data.sql     # Definición de tablas (Clientes, PlazosFijos, Transacciones), FKs e inserts de prueba
│   └── stored_procedures.sql   # SP para simulación de cálculos financieros
│
└── src/
    └── com/
        ├── app/
        │   └── Main.java       # Punto de entrada, armado de portafolio y prueba polimórfica
        └── inversiones/
            └── model/          # Clases de dominio, herencia y lógica de ganancias
```
