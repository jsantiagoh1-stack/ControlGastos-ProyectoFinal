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

**Última actualización:** 23 de octubre de 2026.
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

### Funcionalidades implementadas

- Registro, consulta, edición y eliminación de gastos.
- Registro, consulta, edición y eliminación de ingresos.
- Persistencia de movimientos mediante Room y SQLite.
- Cálculo automático del saldo disponible.
- Manejo de quetzales (GTQ) y dólares (USD).
- Conversión mediante un tipo de cambio manual.
- Conservación del tipo de cambio de cada movimiento.
- Estadísticas de gastos agrupados por categoría.
- Visualización de porcentajes mediante barras de colores.
- Interfaz renovada con tarjetas, iconos y formularios mejorados.

### Próxima etapa de desarrollo

Después de completar los incrementos 6 y 7, ControlGastos cuenta con administración de ingresos y gastos, conversión de monedas, persistencia de datos y estadísticas financieras por categoría.

Las próximas mejoras podrán enfocarse en nuevas funcionalidades de análisis financiero, validaciones adicionales y optimización de la experiencia de usuario.

Cualquier modificación de la base de datos deberá mantener la compatibilidad con los registros existentes para evitar la pérdida de información.


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

### Incremento 7: rediseño de interfaz y estadísticas financieras

**Estado: implementado y probado en dispositivo Android.**

El objetivo de este incremento fue mejorar la presentación visual de ControlGastos y permitir al usuario analizar sus gastos mediante estadísticas organizadas por categoría.

**Mejoras de interfaz implementadas:**

- Rediseño de la pantalla principal con tarjetas para el saldo disponible y el resumen financiero.
- Presentación diferenciada de ingresos y gastos.
- Incorporación de iconos para las categorías de gastos.
- Incorporación de iconos para identificar ingresos en GTQ y USD.
- Rediseño de los formularios de registro y edición de gastos e ingresos.
- Incorporación del botón «Ver estadísticas».
- Uso de colores, tarjetas redondeadas y una distribución visual más clara.

**Estadísticas financieras implementadas:**

- Nueva pantalla de estadísticas.
- Consulta de gastos agrupados por categoría mediante Room.
- Cálculo del total de gastos expresado en quetzales.
- Visualización de la cantidad de gastos registrados.
- Cálculo del porcentaje correspondiente a cada categoría.
- Representación de los porcentajes mediante barras horizontales.
- Asignación de colores diferentes según la categoría.
- Actualización de las estadísticas a partir de los datos almacenados.

**Decisiones técnicas:**

Se utilizó una consulta SQL en `GastoDao` con `GROUP BY` y `SUM` para calcular los gastos acumulados por categoría.

Los gastos registrados en dólares se convierten a quetzales mediante el tipo de cambio almacenado en cada movimiento.

La clase `GastoPorCategoria` representa el nombre de una categoría y su total acumulado.

La clase `EstadisticasActivity` obtiene los resultados mediante `GastoRepositoryRoom` y construye las barras utilizando componentes nativos de Android.

Estas decisiones permiten mantener separadas las responsabilidades de persistencia, consulta de datos y presentación visual.

**Pruebas realizadas:**

Se compiló la aplicación mediante Gradle y se obtuvo el resultado `BUILD SUCCESSFUL`.

Posteriormente, se verificó en el dispositivo Android:

- La visualización de la pantalla principal rediseñada.
- El acceso a la pantalla de estadísticas.
- La carga de gastos almacenados en Room.
- La presentación de los importes por categoría.
- El cálculo de porcentajes.
- La visualización de barras con colores diferentes.
- La actualización de los resultados después de eliminar un movimiento de prueba.

**Resultados finales verificados:**

| Concepto | Resultado |
|---|---:|
| Gastos registrados | 10 |
| Total de gastos | Q1,405.00 |
| Visualización por categorías | Correcta |
| Porcentajes y barras | Correctos |
| Compilación Gradle | Exitosa |

**Resultado del incremento:**

ControlGastos dispone de una interfaz renovada y una pantalla de estadísticas funcional que permite identificar cómo se distribuyen los gastos personales entre las distintas categorías.

La información presentada procede de la base de datos Room y respeta el tipo de cambio guardado para los movimientos en dólares.