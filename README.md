# 📈 Sistema de Inversiones y Rendimientos Diarios

Sistema backend desarrollado en **Java y SQL Server** diseñado para simular la gestión de inversiones financieras, colocación de plazos fijos, compra de acciones, fondos comunes y el seguimiento de saldos y transacciones.

Este proyecto aplica **Programación Orientada a Objetos** (Herencia, Polimorfismo y Encapsulamiento), **Arquitectura en Capas** para desacoplar responsabilidades y el uso intensivo de **T-SQL** para analítica y lógica de base de datos.

---

## 🏦 Funcionalidades Principales

### 💵  Gestión de Plazos Fijos
* Constituir plazos fijos vinculados a clientes, registrando monto, días y Tasa Nominal Anual (TNA).
* Cálculo polimórfico de ganancias basadas en la fórmula de interés según los días estipulados.

### 📉 Acciones y Fondos Comunes de Inversión (FCI)
* Registro y cálculo dinámico de rendimientos de acciones mediante cotizaciones (precio de compra vs. precio actual).
* Cálculo de ganancias en FCIs mediante el seguimiento del valor de cuotaparte inicial y actual.

### 💳 Control de Saldo Dinámico
* Validación defensiva en tiempo de ejecución: el sistema descuenta automáticamente el dinero del saldo disponible del cliente y bloquea operaciones si el saldo es insuficiente.


---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 17+ (Core / SE)
* **Base de Datos:** SQL Server (T-SQL)
* **Control de Versiones:** Git & GitHub

---

## 🗄️ Conceptos Técnicos Aplicados

* **Java & Arquitectura en Capas:**
  * **Separación de Responsabilidades:** Desacoplamiento total dividiendo la interfaz de usuario (`app`), la lógica de negocio y validación de reglas (`service`) y las entidades de dominio (`model`).
  * **Clases Abstractas, Interfaces y Polimorfismo:** Uso de la interfaz `Calculable` y clase base `Inversion`, permitiendo procesar dinámicamente Plazos Fijos, Acciones y FCIs dentro de un mismo portafolio.
  * **Encapsulamiento:** Control de acceso a atributos mediante modificadores `private`, metodos mutadores (`getters/setters`) y actualización del saldo operativo del `Cliente`.

---

## 📁 Estructura del Proyecto

```text
investment-management-system/
│
├── database
│
└── src/
    └── com/
        ├── app/                # Capa de Presentación / UI (Entrada por Consola)
        │   ├── Main.java
        │   ├── Menu_del_Cliente.java
        │   └── Menu_Interactivo.java
        │
        └── inversiones/
            ├── enums
            |   └──TipoOperacion.enum
            |
            ├── exception/      # Capa para manejar excepciones especificas
            |   ├──inversionInvalidaException
            |   └──SaldoInsuficienteException
            |
            ├── model/          # Capa de Dominio (Modelos de datos y Polimorfismo)
            │   ├── Accion.java
            │   ├── Calculable.java
            │   ├── Cliente.java
            │   ├── FondoComunInversion.java
            │   ├── Inversion.java
            |   ├── PlazoFijo.java
            │   └── Transaccion.java
            │
            └── service/        # Capa de Servicios (Lógica del Negocio)
                └── InversionService.java
