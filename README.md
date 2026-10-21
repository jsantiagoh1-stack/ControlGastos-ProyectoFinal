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

**Última actualización:** 20 de octubre de 2026.

ControlGastos es una aplicación Android desarrollada en Kotlin como proyecto académico de Programación 2 de la Universidad Mariano Gálvez de Guatemala.

### Funcionalidades implementadas

- Registro de gastos con descripción, monto, categoría y fecha.
- Visualización de los gastos registrados.
- Edición de gastos existentes.
- Eliminación de gastos con confirmación.
- Almacenamiento persistente mediante SQLite y Room.
- Conservación de los registros al cerrar y volver a abrir la aplicación.
- Integración con la biblioteca Kotlin `gastos-core` versión 1.1.0.

### Tecnologías utilizadas

- Kotlin.
- Android Studio.
- SQLite.
- Room.
- KSP.
- Gradle.
- Git y GitHub.

### Funcionalidades pendientes

Las siguientes mejoras forman parte de la evolución prevista de ControlGastos:

1. Registro y administración de ingresos.
2. Cálculo del saldo disponible.
3. Registro de movimientos en quetzales (GTQ) y dólares estadounidenses (USD).
4. Conversión de monedas mediante un tipo de cambio manual.
5. Almacenamiento del tipo de cambio utilizado en cada movimiento.
6. Resúmenes financieros y estadísticas por categoría.
7. Mejoras en el diseño y la experiencia de usuario.

### Próxima etapa de desarrollo

El siguiente incremento estará enfocado en implementar el registro de ingresos y el cálculo del saldo disponible, manteniendo la persistencia de datos mediante SQLite y Room.

Posteriormente se incorporará el manejo de quetzales y dólares, procurando conservar los datos registrados durante las actualizaciones de la base de datos.
