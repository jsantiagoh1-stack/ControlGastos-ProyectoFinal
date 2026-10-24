# Arquitectura del proyecto ControlGastos

## 1. Introducción

El proyecto ControlGastos utiliza una estructura que separa los modelos de datos, la lógica de negocio y el almacenamiento de información.

Esta organización permite mantener el código ordenado, facilitar su mantenimiento y agregar nuevas funcionalidades sin modificar innecesariamente las clases existentes.

Actualmente, la aplicación Android utiliza SQLite y Room para almacenar los gastos de forma persistente, conservando la independencia de la biblioteca `gastos-core`.

## 2. Estructura de la librería

La biblioteca `gastos-core`, versión `1.1.0`, está desarrollada en Kotlin y contiene los siguientes componentes principales.

### Gasto.kt

Es una clase de datos (`data class`) que representa un gasto registrado por el usuario.

Contiene los atributos:

- `id`: identificador del gasto.
- `descripcion`: nombre o detalle.
- `monto`: cantidad de dinero.
- `categoria`: categoría a la que pertenece.
- `fecha`: fecha del gasto.

Se utiliza una `data class` para facilitar el manejo de los objetos y evitar escribir manualmente métodos comunes.

### Categoria.kt

Es una enumeración (`enum class`) que define las categorías disponibles para clasificar los gastos.

Incluye alimentación, transporte, estudios, entretenimiento, salud y otros.

Se utiliza una enumeración para limitar los valores disponibles y evitar categorías incorrectas.

### GastoRepository.kt

Es una interfaz que establece las operaciones principales del almacenamiento:

- Agregar gastos.
- Listar gastos.
- Buscar gastos por identificador.
- Actualizar gastos.
- Eliminar gastos.

La interfaz permite cambiar la implementación del almacenamiento sin alterar el contrato utilizado por la lógica de negocio.

### GastoRepositoryEnMemoria.kt

Es la implementación inicial de `GastoRepository`.

Utiliza una lista mutable para almacenar temporalmente los gastos y proporciona las operaciones CRUD.

Su principal limitación es que los datos desaparecen cuando termina el proceso de la aplicación.

Esta implementación permanece disponible en la biblioteca, aunque la aplicación Android utiliza actualmente una implementación persistente.

### ControlGastos.kt

Contiene la lógica de negocio del sistema.

Sus responsabilidades incluyen:

- Validar los datos de los gastos.
- Registrar y actualizar gastos.
- Buscar, listar y eliminar gastos.
- Calcular el total de gastos.
- Calcular totales por categoría.
- Generar un resumen financiero de gastos.

Esta clase recibe una implementación de `GastoRepository` mediante su constructor.

## 3. Persistencia de datos en Android

La aplicación Android utiliza SQLite mediante la biblioteca Room.

Para implementar este almacenamiento se incorporaron los siguientes componentes.

### GastoEntity.kt

Representa los registros de la tabla `gastos`.

Sus campos son:

- `id`: identificador único.
- `descripcion`: descripción del gasto.
- `monto`: cantidad registrada.
- `categoria`: categoría almacenada como texto.
- `fecha`: fecha del gasto.

### GastoDao.kt

Define las operaciones de acceso a los datos:

- Insertar registros.
- Consultar todos los gastos.
- Buscar por identificador.
- Actualizar registros.
- Eliminar registros.

Utiliza las anotaciones de Room y funciones suspendidas para ejecutar las operaciones.

### AppDatabase.kt

Es la clase que define la base de datos utilizada por Room.

Actualmente utiliza la versión 1 y proporciona acceso a `GastoDao`.

### GastoRepositoryRoom.kt

Implementa el contrato `GastoRepository` de la biblioteca `gastos-core`.

Su responsabilidad es conectar la lógica del sistema con SQLite mediante Room.

También transforma los objetos de tipo `Gasto` en entidades `GastoEntity` y viceversa.

### ControlGastosApp.kt

Es la clase principal de aplicación que hereda de `Application`.

Inicializa los componentes necesarios para utilizar la base de datos.

### Dependencias.kt

Centraliza la instancia compartida del servicio `ControlGastos`.

Actualmente configura el sistema utilizando `GastoRepositoryRoom` en lugar de `GastoRepositoryEnMemoria`.

## 4. Decisiones de diseño

**Separación de responsabilidades:** los modelos representan la información, el repositorio administra el almacenamiento y el servicio contiene las reglas de negocio.

**Uso de interfaces:** permite intercambiar implementaciones de almacenamiento manteniendo el mismo contrato.

**Encapsulamiento:** las propiedades privadas protegen los datos internos de las clases.

**Uso de corrutinas:** los métodos `suspend` permiten trabajar con operaciones de almacenamiento que pueden requerir tiempo de espera.

**Reutilización del código:** la biblioteca `gastos-core` es independiente de Android y puede utilizarse tanto desde consola como desde una aplicación móvil.

**Persistencia con Room:** permite conservar los gastos después de cerrar y volver a abrir la aplicación.

**Patrón Repository:** separa las operaciones de almacenamiento de la lógica principal del sistema.

## 5. Evolución del almacenamiento

### Versión inicial

La versión base almacenaba la información mediante `GastoRepositoryEnMemoria`.

Los gastos permanecían disponibles únicamente durante la ejecución del proceso.

Al cerrar la aplicación, los registros dejaban de estar disponibles.

### Incremento 4: SQLite y Room

Se implementó el almacenamiento persistente mediante `GastoRepositoryRoom`.

Este repositorio utiliza `GastoDao` para consultar y modificar la base de datos SQLite.

La aplicación fue compilada correctamente y probada en un teléfono Samsung SM-A165M.

Durante las pruebas se confirmó que los gastos permanecían registrados después de cerrar y volver a abrir la aplicación.

También se comprobó el funcionamiento de la opción para eliminar gastos.

La implementación conservó el contrato `GastoRepository` y la separación entre la biblioteca Kotlin y los componentes Android.

## 6. Estado actual del proyecto

Las siguientes funcionalidades están implementadas:

- Registro de gastos.
- Visualización de gastos.
- Edición de gastos.
- Eliminación de gastos.
- Almacenamiento persistente con SQLite y Room.
- Conservación de los gastos después de cerrar y volver a abrir la aplicación.
- Integración con `gastos-core` versión `1.1.0`.

La base de datos se encuentra actualmente en la versión 1.

## 7. Mejoras previstas

Durante el desarrollo del proyecto final se incorporarán progresivamente las siguientes funcionalidades:

1. Registro y administración de ingresos.
2. Cálculo del saldo disponible.
3. Manejo de movimientos en quetzales y dólares.
4. Conversión de monedas mediante un tipo de cambio manual.
5. Conservación del tipo de cambio utilizado en cada movimiento.
6. Estadísticas y resúmenes financieros.
7. Mejoras en la interfaz de usuario.
8. Ampliación de las pruebas funcionales.

Las modificaciones futuras de la base de datos deberán considerar migraciones de Room para conservar los registros existentes.

## 8. Conclusión

La arquitectura actual de ControlGastos mantiene separadas la lógica de negocio, la interfaz Android y la persistencia.

Gracias al contrato `GastoRepository`, fue posible sustituir el almacenamiento temporal por SQLite y Room sin reescribir la lógica principal de la biblioteca.

Esta estructura proporciona una base para continuar desarrollando funcionalidades de administración de ingresos, saldo disponible, conversión de monedas y estadísticas financieras.

## 9. Incremento 5: administración de ingresos

El Incremento 5 amplió las funcionalidades de ControlGastos para administrar tanto gastos como ingresos personales.

Se incorporaron las clases `IngresoEntity`, `IngresoDao` e `IngresoRepositoryRoom` para gestionar ingresos mediante SQLite y Room.

También se desarrollaron `FormularioIngresoActivity` e `IngresoAdapter` para permitir el registro, consulta, edición y eliminación de ingresos desde la aplicación Android.

### Actualización de la base de datos

La base de datos pasó de la versión 1 a la versión 2.

Se implementó `MIGRATION_1_2`, que crea la tabla `ingresos` sin eliminar los gastos almacenados anteriormente.

Esta migración permitió ampliar la estructura de almacenamiento manteniendo los registros existentes.

### Cálculo del saldo disponible

La aplicación calcula el saldo mediante la siguiente fórmula:

**Saldo disponible = Total de ingresos − Total de gastos**

Los valores se obtienen de los repositorios correspondientes y se actualizan al regresar a la pantalla principal.

### Pruebas realizadas

Se verificó el funcionamiento de las operaciones CRUD de ingresos y la actualización del saldo en un Samsung SM-A165M.

También se comprobó que los ingresos permanecen almacenados después de cerrar y volver a abrir la aplicación.

### Estado actual

Con este incremento, ControlGastos dispone de administración de gastos e ingresos, persistencia mediante SQLite y Room, y cálculo del saldo disponible.

Las próximas mejoras contemplan el uso de quetzales y dólares, tipos de cambio manuales y estadísticas financieras.

## Incremento 6: administración de movimientos en GTQ y USD

### Objetivo

Ampliar ControlGastos para permitir el registro y administración de gastos e ingresos en quetzales guatemaltecos (GTQ) y dólares estadounidenses (USD), utilizando un tipo de cambio manual.

### Cambios en el modelo de datos

Se incorporaron los atributos `moneda` y `tipoCambio` a las entidades `GastoEntity` e `IngresoEntity`.

Estos campos permiten almacenar el monto en su moneda original y conservar el tipo de cambio utilizado en cada movimiento.

La biblioteca `gastos-core` se mantiene compatible con las funcionalidades existentes. La información monetaria adicional se administra desde la aplicación Android mediante Room.

### Actualización de la base de datos

La base de datos SQLite se actualizó de la versión 2 a la versión 3 mediante `MIGRATION_2_3`.

La migración agregó las columnas `moneda` y `tipoCambio` a las tablas de gastos e ingresos, asignando valores predeterminados de `GTQ` y `1.0` a los registros existentes.

Esto permitió incorporar el manejo de monedas sin eliminar los movimientos registrados anteriormente.

### Componentes modificados

**GastoRepositoryRoom.kt**

Incorpora funciones para registrar y actualizar gastos con moneda y tipo de cambio, consultar la información monetaria y calcular los gastos expresados en GTQ.

**IngresoRepositoryRoom.kt**

Calcula los ingresos expresados en GTQ utilizando el tipo de cambio almacenado en cada movimiento en USD.

**FormularioGastoActivity.kt y FormularioIngresoActivity.kt**

Permiten seleccionar GTQ o USD, introducir manualmente el tipo de cambio y visualizar el equivalente en quetzales antes de guardar.

**GastoAdapter.kt e IngresoAdapter.kt**

Presentan los movimientos en su moneda original y muestran el equivalente en quetzales cuando corresponde.

**MainActivity.kt**

Calcula y presenta el total de ingresos, el total de gastos y el saldo disponible utilizando GTQ como moneda de referencia.

### Reglas de conversión

Para los movimientos en GTQ:

**Equivalente en GTQ = Monto original.**

Para los movimientos en USD:

**Equivalente en GTQ = Monto original × Tipo de cambio.**

El saldo disponible se obtiene mediante:

**Saldo disponible = Ingresos convertidos a GTQ − Gastos convertidos a GTQ.**

El tipo de cambio se almacena individualmente en cada registro y no depende de una conexión a internet.

### Pruebas realizadas

Se verificó el registro, consulta, edición, eliminación y persistencia de movimientos en USD en un Samsung SM-A165M.

También se comprobó que los totales financieros se actualizan correctamente y que los gastos e ingresos anteriores permanecen almacenados.

Las pruebas están documentadas en `docs/pruebas-monedas-incremento6.md`.

### Estado del Incremento 6

**Implementado y probado.**

Las próximas mejoras estarán orientadas a estadísticas financieras por categoría, resúmenes y mejoras visuales de la aplicación.
