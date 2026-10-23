# Pruebas del Incremento 5 — ControlGastos

## 1. Objetivo

Comprobar el funcionamiento de la administración de ingresos, las operaciones CRUD y el cálculo del saldo disponible en la aplicación Android ControlGastos.

## 2. Entorno de pruebas

- **Aplicación:** ControlGastos.
- **Lenguaje:** Kotlin.
- **Dispositivo:** Samsung SM-A165M.
- **Base de datos:** SQLite mediante Room.
- **Fecha de pruebas:** 22 de octubre de 2026.

## 3. Pruebas realizadas

| Prueba | Resultado |
|---|---|
| Compilación del proyecto Android | Correcta: BUILD SUCCESSFUL |
| Ejecución de la aplicación en Samsung SM-A165M | Correcta |
| Registro de ingresos | Correcto |
| Validación de descripción, monto y fecha | Implementada; no se documentó una prueba individual de cada validación |
| Visualización del historial de ingresos | Correcta |
| Consulta de ingresos existentes | Correcta |
| Edición de un ingreso | Correcta |
| Eliminación con confirmación | Correcta |
| Actualización automática del saldo | Correcta |
| Conservación de los datos al cerrar y reabrir la aplicación | Correcta |

## 4. Prueba de registro

Se registraron ingresos de prueba con los siguientes datos:

- Descripción: Salario.
- Monto: Q 2,500.00.
- Fecha: 2026-10-22.

La aplicación mostró el mensaje de confirmación y almacenó los registros mediante Room.

## 5. Prueba de edición

Se seleccionó un ingreso del historial y se modificó su descripción a «Salario actualizado».

Se comprobó que el cambio apareció en el historial sin crear un registro adicional. Como el monto no cambió, el saldo disponible permaneció igual.

## 6. Prueba de eliminación

Se seleccionó uno de los ingresos y se utilizó la opción «Eliminar ingreso».

La aplicación solicitó confirmación antes de eliminar el registro.

Después de confirmar, el total de ingresos disminuyó de Q 7,500.00 a Q 5,000.00, mientras que los gastos permanecieron en Q 1,405.00.

## 7. Prueba del saldo disponible

El cálculo utilizado es:

**Saldo disponible = Total de ingresos − Total de gastos**

Los resultados finales fueron:

| Concepto | Resultado |
|---|---:|
| Cantidad de ingresos | 2 |
| Total de ingresos | Q 5,000.00 |
| Cantidad de gastos | 10 |
| Total de gastos | Q 1,405.00 |
| Saldo disponible | Q 3,595.00 |

Se verificó que el saldo se actualiza después de modificar los movimientos.

## 8. Prueba de persistencia

Se cerró la aplicación y posteriormente se abrió nuevamente desde el dispositivo.

Los dos ingresos registrados continuaron disponibles:

- Salario — Q 2,500.00.
- Salario actualizado — Q 2,500.00.

También permanecieron los diez gastos y el saldo disponible de Q 3,595.00.

Este resultado confirma la persistencia de los datos durante la prueba realizada.

## 9. Conclusión

El Incremento 5 permitió incorporar el registro, consulta, edición y eliminación de ingresos sin perder la funcionalidad de gastos existente.

Las pruebas realizadas demostraron que la aplicación puede calcular el saldo disponible, actualizarlo después de las operaciones y conservar la información mediante SQLite y Room.

El siguiente incremento se orientará al manejo de quetzales y dólares mediante tipos de cambio manuales.