# Pruebas de persistencia con Room — ControlGastos

## 1. Objetivo

Comprobar el funcionamiento de la aplicación ControlGastos después de implementar la persistencia de datos mediante SQLite y Room, correspondiente al Incremento 4 del proyecto.

El propósito principal es verificar que los gastos registrados permanezcan almacenados después de cerrar y volver a abrir la aplicación.

## 2. Entorno de pruebas

- **Proyecto:** ControlGastos.
- **Lenguaje:** Kotlin.
- **Entorno de desarrollo:** Android Studio.
- **Dispositivo de prueba:** Samsung SM-A165M.
- **Base de datos:** SQLite.
- **Biblioteca de persistencia:** Room.
- **Biblioteca de lógica de negocio:** gastos-core 1.1.0.
- **Control de versiones:** Git y GitHub.

## 3. Pruebas realizadas

| Prueba | Resultado |
|---|---|
| Compilación del proyecto Android | Correcta: BUILD SUCCESSFUL |
| Ejecución en Samsung SM-A165M | Correcta |
| Registro de gastos | Correcto |
| Visualización de gastos | Correcta |
| Edición de gastos | Funcionalidad implementada |
| Eliminación de gastos | Probada correctamente |
| Conservación de registros al cerrar y abrir la aplicación | Correcta |
| Conexión de ControlGastos con GastoRepositoryRoom | Implementada |
| Almacenamiento mediante SQLite y Room | Implementado y comprobado mediante persistencia |
| Publicación del código en GitHub | Correcta |

## 4. Prueba de almacenamiento persistente

Se registraron gastos mediante el formulario de la aplicación Android.

Posteriormente, se cerró la aplicación y se volvió a abrir en el dispositivo Samsung SM-A165M.

**Resultado obtenido:** los gastos registrados continuaron apareciendo en la lista.

Esto permitió comprobar que la aplicación ya no depende exclusivamente del almacenamiento temporal en memoria.

## 5. Prueba de eliminación

Se utilizó la opción Eliminar disponible al editar un gasto existente.

La aplicación permitió realizar la operación de eliminación.

**Resultado obtenido:** la eliminación funcionó correctamente durante las pruebas en el dispositivo.

## 6. Implementación técnica verificada

La persistencia se implementó mediante los siguientes componentes:

- `GastoEntity.kt`: representa los gastos almacenados.
- `GastoDao.kt`: permite insertar, consultar, actualizar y eliminar registros.
- `AppDatabase.kt`: define la base de datos de Room.
- `GastoRepositoryRoom.kt`: comunica la biblioteca gastos-core con Room.
- `ControlGastosApp.kt`: inicializa los componentes de la aplicación.
- `Dependencias.kt`: proporciona el servicio ControlGastos utilizando el repositorio persistente.
- `AndroidManifest.xml`: registra ControlGastosApp como clase de aplicación.

La estructura utiliza el contrato `GastoRepository` de la biblioteca independiente gastos-core.

## 7. Comparación entre la versión inicial y el Incremento 4

| Característica | Versión inicial | Incremento 4 |
|---|---|---|
| Almacenamiento | Memoria RAM | SQLite con Room |
| Registro de gastos | Disponible | Disponible |
| Consulta de gastos | Disponible | Disponible |
| Edición de gastos | Disponible | Disponible |
| Eliminación desde el formulario | No disponible | Disponible |
| Persistencia al cerrar la aplicación | No | Sí |
| Repositorio utilizado | GastoRepositoryEnMemoria | GastoRepositoryRoom |

## 8. Evidencias

Durante el desarrollo se observaron compilaciones exitosas en Android Studio, incluyendo el mensaje `BUILD SUCCESSFUL`.

También se realizaron pruebas de funcionamiento en un teléfono Samsung SM-A165M y se comprobó que los registros permanecían después de cerrar y volver a abrir la aplicación.

Las capturas correspondientes pueden incorporarse posteriormente como evidencia visual complementaria.

## 9. Funcionalidades pendientes

La implementación actual constituye una base para continuar desarrollando:

1. Registro y administración de ingresos.
2. Cálculo del saldo disponible.
3. Manejo de quetzales y dólares.
4. Conversión de monedas con tipo de cambio manual.
5. Conservación del tipo de cambio aplicado a cada movimiento.
6. Estadísticas y resúmenes financieros.
7. Mejoras de diseño y experiencia de usuario.

## 10. Conclusión

Las pruebas realizadas demostraron que ControlGastos puede almacenar los gastos de manera persistente mediante SQLite y Room.

A diferencia de la versión inicial, los registros permanecen disponibles después de cerrar y volver a abrir la aplicación.

Con la implementación del Incremento 4 se completó una parte fundamental de la arquitectura, permitiendo continuar el desarrollo de nuevas funcionalidades financieras sin comenzar nuevamente el proyecto.
