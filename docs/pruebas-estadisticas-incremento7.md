# Pruebas del Incremento 7 — ControlGastos

## 1. Objetivo

Comprobar el funcionamiento del rediseño visual de ControlGastos y de las estadísticas financieras por categoría, verificando que los resultados se obtengan de los movimientos almacenados mediante SQLite y Room.

## 2. Entorno de pruebas

- **Aplicación:** ControlGastos.
- **Lenguaje:** Kotlin.
- **Dispositivo:** Samsung SM-A165M.
- **Base de datos:** SQLite mediante Room, versión 3.
- **Fecha de pruebas:** 23 de octubre de 2026.

## 3. Funcionalidades verificadas

| Prueba | Resultado |
|---|---|
| Compilación del proyecto mediante Gradle | Correcta |
| Visualización de la pantalla principal rediseñada | Correcta |
| Presentación del saldo disponible | Correcta |
| Visualización de ingresos y gastos | Correcta |
| Iconos de categorías de gastos | Correcta |
| Iconos de ingresos según moneda | Correcta |
| Formularios de gastos e ingresos rediseñados | Correcta |
| Acceso mediante el botón «Ver estadísticas» | Correcta |
| Consulta de gastos agrupados por categoría | Correcta |
| Presentación del total de gastos | Correcta |
| Visualización de cantidad de movimientos | Correcta |
| Cálculo de porcentajes por categoría | Correcta |
| Visualización de barras horizontales | Correcta |
| Colores diferentes según categoría | Correcta |
| Actualización de estadísticas después de eliminar un gasto | Correcta |

## 4. Prueba del rediseño visual

Se ejecutó ControlGastos en el dispositivo Android para verificar la presentación de la nueva interfaz.

Se comprobó que la pantalla principal mostraba las tarjetas de resumen financiero, los botones de registro y los historiales de ingresos y gastos.

También se revisaron los iconos de los movimientos y la nueva presentación de los formularios.

El rediseño mantuvo las funcionalidades existentes de registro, consulta, edición y eliminación.

## 5. Prueba de estadísticas financieras

Desde la pantalla principal se seleccionó el botón «Ver estadísticas».

La nueva pantalla mostró:

- El total general de gastos expresado en GTQ.
- La cantidad de gastos registrados.
- Los importes acumulados por categoría.
- El porcentaje correspondiente a cada categoría.
- Barras horizontales para representar los porcentajes.

La información se obtuvo mediante la consulta `obtenerTotalesPorCategoria()` de `GastoDao`.

Esta consulta agrupa los registros por categoría y convierte los movimientos USD a GTQ utilizando el tipo de cambio almacenado en cada movimiento.

## 6. Prueba de actualización de estadísticas

Durante las verificaciones se identificó un gasto de prueba de US$20.00, equivalente a Q154.00, utilizando un tipo de cambio de Q7.70 por dólar.

Mientras el movimiento estaba registrado, las estadísticas mostraron:

| Concepto | Resultado |
|---|---:|
| Gastos registrados | 11 |
| Total de gastos | Q1,559.00 |

Posteriormente, se eliminó el movimiento de prueba.

Al consultar nuevamente las estadísticas, los resultados fueron:

| Concepto | Resultado |
|---|---:|
| Gastos registrados | 10 |
| Total de gastos | Q1,405.00 |

Se comprobó que las estadísticas reflejaron los cambios de los datos almacenados.

## 7. Prueba de barras por categoría

Se verificó que las barras horizontales representaran los porcentajes de cada categoría respecto al total general de gastos.

Se asignaron colores diferenciados:

| Categoría | Color |
|---|---|
| Alimentación | Naranja |
| Transporte | Verde |
| Estudios | Azul |
| Entretenimiento | Morado |
| Salud | Rosado |
| Otros | Gris azulado |

En la prueba visual se comprobó que las categorías mostraban los colores correspondientes y conservaban sus importes y porcentajes.

## 8. Resultados finales

| Concepto | Resultado |
|---|---:|
| Gastos registrados | 10 |
| Total de gastos | Q1,405.00 |
| Pantalla principal rediseñada | Correcta |
| Pantalla de estadísticas | Correcta |
| Barras por categoría | Correctas |
| Compilación Gradle | BUILD SUCCESSFUL |

La consulta de estadísticas se implementó sobre las tablas existentes, por lo que no fue necesario incrementar la versión de Room ni introducir una nueva migración.

## 9. Conclusión

El Incremento 7 incorporó correctamente el rediseño de la interfaz y las estadísticas financieras por categoría.

Se verificó la consulta de información almacenada, la presentación de los totales, el cálculo de porcentajes, la visualización de barras de colores y la actualización de resultados después de eliminar un movimiento de prueba.

La implementación conserva las funcionalidades de los incrementos anteriores y mantiene la separación de responsabilidades entre actividades Android, repositorios y Room.