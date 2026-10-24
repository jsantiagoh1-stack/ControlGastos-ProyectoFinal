\# ControlGastos — Proyecto Final



\## Descripción del proyecto



ControlGastos es un proyecto académico desarrollado en Kotlin cuyo propósito es facilitar el registro y la administración de gastos personales.



El proyecto busca evolucionar hacia una aplicación Android sencilla, intuitiva y accesible para cualquier usuario, incorporando almacenamiento persistente, control de ingresos y gastos, estadísticas financieras y conversión de monedas.



\## Objetivo general



Desarrollar una aplicación móvil que permita administrar movimientos financieros personales mediante una interfaz fácil de utilizar, aplicando los conocimientos adquiridos en Programación II.



\## Estado inicial del proyecto



El proyecto parte de dos componentes proporcionados como base para el desarrollo:



\*\*1. ControlGastos-core\*\*



Librería Kotlin que contiene los modelos, las reglas de negocio, las validaciones y el repositorio de gastos en memoria.



\*\*2. ControlGastos-app\*\*



Aplicación Android que proporciona las pantallas para visualizar, registrar y editar gastos.



En esta versión inicial, los gastos se almacenan temporalmente en memoria.



\## Funcionalidades previstas



\- Registro, consulta, edición y eliminación de gastos.

\- Persistencia de información mediante SQLite y Room.

\- Registro y administración de ingresos.

\- Consulta del saldo y resumen financiero.

\- Conversión entre quetzales y dólares mediante un tipo de cambio manual.

\- Conservación del tipo de cambio aplicado a cada movimiento.

\- Estadísticas de gastos por categoría.

\- Interfaz sencilla y accesible.



\## Tecnologías



\- Kotlin

\- Android Studio

\- XML

\- Git y GitHub



Se incorporarán SQLite y Room en las siguientes etapas.



\## Metodología de desarrollo



El desarrollo se realizará por incrementos. Cada etapa incluirá implementación, pruebas, documentación y un commit de Git.



Esto permitirá conservar un historial verificable de las modificaciones y explicar las decisiones tomadas durante el desarrollo.



\## Estado actual



\*\*Etapa 1: proyectos base incorporados al repositorio.\*\*



Las funcionalidades adicionales se implementarán progresivamente y se documentarán conforme sean completadas.



\## Propósito académico



Proyecto final de Programación II, orientado a demostrar conocimientos de programación orientada a objetos, manejo de datos, persistencia, interfaces móviles y control de versiones.


## Estado actual del proyecto

**Última actualización:** 22 de octubre de 2026.

ControlGastos es una aplicación Android desarrollada en Kotlin como proyecto académico de Programación II de la Universidad Mariano Gálvez de Guatemala.

Actualmente se han implementado la persistencia de gastos mediante Room y SQLite, así como la administración de ingresos y el cálculo del saldo disponible.

### Incremento 4: persistencia de gastos con Room y SQLite

**Estado: completado y probado.**

Funcionalidades implementadas:

- Registro de gastos con descripción, monto, categoría y fecha.
- Visualización de los gastos registrados.
- Edición de gastos existentes.
- Eliminación de gastos con confirmación.
- Almacenamiento persistente mediante SQLite y Room.
- Conservación de los registros al cerrar y volver a abrir la aplicación.
- Integración con la biblioteca Kotlin `gastos-core` versión 1.1.0.

### Incremento 5: administración de ingresos y saldo disponible

**Estado: implementado y probado.**

Funcionalidades desarrolladas:

- Registro de ingresos con descripción, monto y fecha.
- Validación de los datos ingresados.
- Almacenamiento de ingresos mediante SQLite y Room.
- Historial de ingresos registrados.
- Edición de ingresos existentes.
- Eliminación de ingresos con ventana de confirmación.
- Actualización de los totales después de registrar, editar o eliminar movimientos.
- Cálculo automático del saldo disponible mediante la diferencia entre ingresos y gastos.
- Conservación de los registros después de cerrar y volver a abrir la aplicación.

**Fórmula utilizada:**

Saldo disponible = Total de ingresos − Total de gastos.

### Pruebas realizadas del Incremento 5

Las pruebas se realizaron en un dispositivo Samsung SM-A165M.

Se verificó el funcionamiento del registro, la consulta, la edición y la eliminación de ingresos, así como el cálculo del saldo disponible.

Durante la prueba final se obtuvieron los siguientes resultados:

| Concepto | Resultado |
|---|---:|
| Ingresos registrados | 2 |
| Total de ingresos | Q 5,000.00 |
| Gastos registrados | 10 |
| Total de gastos | Q 1,405.00 |
| Saldo disponible | Q 3,595.00 |

Se comprobó que, al cerrar y volver a abrir la aplicación, los ingresos y gastos permanecieron almacenados y los totales se conservaron correctamente.

### Tecnologías utilizadas

- Kotlin.
- Android Studio.
- XML y ViewBinding.
- SQLite.
- Room.
- KSP.
- Gradle.
- Corrutinas de Kotlin.
- Git y GitHub.

### Funcionalidades pendientes

Las siguientes mejoras forman parte de las próximas etapas de ControlGastos:

1. Registro de movimientos en quetzales (GTQ) y dólares estadounidenses (USD).
2. Conversión de monedas mediante un tipo de cambio manual.
3. Conservación del tipo de cambio aplicado a cada movimiento.
4. Estadísticas financieras y resúmenes por categoría.
5. Mejoras en el diseño y la experiencia de usuario.

### Próxima etapa de desarrollo

El siguiente incremento estará orientado al manejo de quetzales y dólares estadounidenses.

Se implementará la conversión de monedas utilizando un tipo de cambio ingresado manualmente y se buscará conservar el valor aplicado a cada movimiento.

Las próximas modificaciones deberán mantener la compatibilidad con la base de datos existente para evitar la pérdida de gastos e ingresos registrados.


### Incremento 6: manejo de quetzales y dólares

**Estado: implementado y probado en dispositivo Android.**

Se incorporó el manejo de dos monedas:

- Quetzales guatemaltecos (GTQ).
- Dólares estadounidenses (USD).

**Funcionalidades implementadas:**

- Selección de moneda al registrar o editar gastos.
- Selección de moneda al registrar o editar ingresos.
- Introducción manual del tipo de cambio para movimientos en USD.
- Cálculo automático del equivalente en quetzales.
- Almacenamiento del monto original, moneda y tipo de cambio utilizado en cada movimiento.
- Visualización de movimientos USD con su monto original y equivalente en GTQ.
- Cálculo de ingresos, gastos y saldo disponible expresados en quetzales.
- Conservación del tipo de cambio al cerrar y volver a abrir la aplicación.
- Migración de Room de la versión 2 a la versión 3, conservando los movimientos anteriores.

**Fórmula utilizada:**

Equivalente en GTQ = Monto en USD × Tipo de cambio.

**Pruebas realizadas:**

Se verificaron el registro, edición, eliminación y persistencia de movimientos en USD desde un Samsung SM-A165M.

Durante las pruebas se utilizaron tipos de cambio manuales de Q7.70 por dólar.

Se comprobó que los movimientos anteriores se conservaron después de actualizar la base de datos.

### Estado financiero al finalizar las pruebas

| Concepto | Resultado |
|---|---:|
| Ingresos registrados | 3 |
| Gastos registrados | 10 |
| Total de ingresos | Q5,770.00 |
| Total de gastos | Q1,405.00 |
| Saldo disponible | Q4,365.00 |

Los resultados incluyen un ingreso de prueba de US$100.00, equivalente a Q770.00.