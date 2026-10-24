# Pruebas del Incremento 6 — ControlGastos

## 1. Objetivo

Comprobar el funcionamiento del registro y administración de movimientos financieros en quetzales (GTQ) y dólares estadounidenses (USD), utilizando un tipo de cambio manual y conservando la información mediante SQLite y Room.

## 2. Entorno de pruebas

- **Aplicación:** ControlGastos.
- **Lenguaje:** Kotlin.
- **Dispositivo:** Samsung SM-A165M.
- **Base de datos:** SQLite mediante Room, versión 3.
- **Fecha de pruebas:** 23 de octubre de 2026.

## 3. Funcionalidades verificadas

| Prueba | Resultado |
|---|---|
| Compilación del proyecto | Correcta |
| Selección de GTQ y USD | Correcta |
| Mostrar el tipo de cambio al seleccionar USD | Correcta |
| Cálculo automático del equivalente en quetzales | Correcto |
| Registro de gastos en USD | Correcto |
| Edición de descripción de gastos en USD | Correcta |
| Edición de monto de gastos en USD | Correcta |
| Eliminación de gastos en USD | Correcta |
| Registro y actualización de ingresos en USD | Correctos |
| Visualización del historial con moneda original | Correcta |
| Actualización automática de ingresos, gastos y saldo | Correcta |
| Persistencia después de cerrar y reabrir la aplicación | Correcta |

## 4. Prueba de gastos en dólares

Se registró el siguiente movimiento:

- **Descripción:** Compra de prueba USD.
- **Monto original:** US$20.00.
- **Moneda:** USD.
- **Tipo de cambio manual:** Q7.70 por dólar.
- **Categoría:** Otros.

El equivalente calculado fue:

20 × 7.70 = Q154.00.

Al guardar el gasto, el total de gastos aumentó de Q1,405.00 a Q1,559.00 y el saldo disponible disminuyó de Q3,595.00 a Q3,441.00.

### Prueba de edición

Se modificó la descripción a «COMPRA USD EDITADA», conservando el monto y el tipo de cambio.

Posteriormente, se actualizó el monto de US$20.00 a US$25.00, manteniendo el cambio en Q7.70.

El nuevo equivalente fue:

25 × 7.70 = Q192.50.

El total de gastos aumentó a Q1,597.50 y el saldo disponible quedó en Q3,402.50.

### Prueba de persistencia y eliminación

Se cerró y volvió a abrir la aplicación, comprobando que el gasto en dólares y su equivalente permanecían registrados.

Posteriormente, se eliminó el gasto de prueba mediante el formulario.

El total de gastos regresó a Q1,405.00 y permanecieron los diez gastos originales.

## 5. Prueba de ingresos en dólares

Se trabajó con un ingreso de prueba con los siguientes valores:

- **Descripción:** INGRESO USD.
- **Monto original:** US$100.00.
- **Moneda:** USD.
- **Tipo de cambio manual:** Q7.70 por dólar.

El equivalente calculado fue:

100 × 7.70 = Q770.00.

El historial mostró el monto original en USD y su equivalente en quetzales.

También se comprobó que el saldo disponible utiliza el equivalente en GTQ para calcular los totales.

### Corrección de registros de prueba

Durante el desarrollo se detectó que la primera versión del formulario de ingresos no almacenaba la moneda seleccionada.

Se corrigió el guardado incorporando los campos `moneda` y `tipoCambio` de `IngresoEntity`.

Posteriormente se actualizó un ingreso de prueba y se eliminó otro registro adicional de Q100.00 para evitar que afectara el saldo.

## 6. Prueba de persistencia de ingresos

Se cerró y volvió a abrir la aplicación.

El ingreso en USD continuó almacenado y se verificó que el formulario recuperaba:

- Monto: US$100.00.
- Moneda: USD.
- Tipo de cambio: Q7.70.
- Equivalente: Q770.00.

## 7. Resultados finales

| Concepto | Resultado |
|---|---:|
| Gastos registrados | 10 |
| Total de gastos | Q1,405.00 |
| Ingresos registrados | 3 |
| Total de ingresos | Q5,770.00 |
| Saldo disponible | Q4,365.00 |

Los registros anteriores a la actualización de Room se conservaron.

## 8. Conclusión

El Incremento 6 incorporó correctamente el manejo de gastos e ingresos en GTQ y USD.

Se verificó la conversión mediante un tipo de cambio manual, el almacenamiento de la moneda y el tipo de cambio de cada movimiento, la actualización de los totales y la persistencia de la información.

La nueva funcionalidad se implementó manteniendo la integración con la biblioteca `gastos-core` y utilizando Room para conservar los datos monetarios adicionales.