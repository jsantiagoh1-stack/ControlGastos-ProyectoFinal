# Arquitectura Android — ControlGastos

## 1. Introducción

ControlGastos es una aplicación Android desarrollada en Kotlin que permite registrar, consultar, editar y eliminar gastos personales.

La aplicación utiliza una librería independiente llamada `gastos-core`, versión `1.1.0`, encargada de los modelos de datos, las validaciones y las reglas de negocio.

Actualmente, los gastos se almacenan de forma persistente mediante SQLite y Room, por lo que permanecen guardados incluso después de cerrar y volver a abrir la aplicación.

El proyecto continuará evolucionando para incorporar ingresos, saldo disponible, manejo de quetzales y dólares, y estadísticas financieras.

## 2. Organización de la aplicación

El proyecto Android está organizado mediante clases Kotlin y archivos XML.

Las clases Kotlin controlan el comportamiento de las pantallas, las operaciones de almacenamiento y la comunicación con la lógica de negocio. Los archivos XML definen la estructura visual.

### MainActivity.kt

Es la pantalla principal de la aplicación.

Sus responsabilidades son:

- Mostrar la lista de gastos registrados.
- Presentar la cantidad de gastos y el total acumulado.
- Abrir el formulario para crear nuevos gastos.
- Abrir el formulario para editar gastos existentes.
- Actualizar la información al regresar del formulario.

Utiliza `RecyclerView` para presentar los registros y `lifecycleScope` para ejecutar operaciones suspendidas.

### FormularioGastoActivity.kt

Controla el formulario utilizado para registrar, editar y eliminar gastos.

Tiene dos modos de funcionamiento:

**Modo crear:** permite introducir la descripción, el monto y la categoría de un gasto nuevo.

**Modo editar:** busca el gasto mediante su identificador, carga sus datos y permite modificarlos o eliminarlos.

La eliminación requiere confirmación del usuario para evitar borrados accidentales.

Utiliza `ControlGastos` para ejecutar las operaciones y aplicar las validaciones de la biblioteca.

### GastoAdapter.kt

Es el adaptador encargado de conectar los objetos `Gasto` con las filas del `RecyclerView`.

Sus principales responsabilidades son:

- Crear y reutilizar las vistas de cada fila.
- Mostrar la descripción, categoría, fecha y monto.
- Actualizar los elementos de la lista.
- Notificar a `MainActivity` cuando el usuario selecciona un gasto.

### Dependencias.kt

Centraliza la instancia compartida de `ControlGastos`.

Actualmente utiliza `GastoRepositoryRoom`, que permite almacenar la información de forma persistente.

La inicialización se realiza mediante:

```kotlin
fun inicializar(app: ControlGastosApp) {
    val repositorio = GastoRepositoryRoom(
        app.database.gastoDao()
    )

    control = ControlGastos(repositorio)
}
```

De esta manera, las distintas pantallas utilizan el mismo servicio y repositorio.

### ControlGastosApp.kt

Es la clase principal de aplicación y hereda de `Application`.

Sus responsabilidades son:

- Proporcionar acceso a la base de datos Room.
- Inicializar las dependencias compartidas al iniciar la aplicación.
- Permitir que las pantallas utilicen el servicio `ControlGastos`.

Se encuentra registrada en `AndroidManifest.xml` mediante:

```xml
android:name=".ControlGastosApp"
```

### GastoEntity.kt

Representa la tabla `gastos` dentro de SQLite.

Contiene los campos:

- `id`: identificador único.
- `descripcion`: descripción del gasto.
- `monto`: cantidad registrada.
- `categoria`: categoría almacenada como texto.
- `fecha`: fecha del gasto.

### GastoDao.kt

Es la interfaz DAO de Room.

Permite ejecutar las operaciones necesarias sobre la tabla `gastos`:

- Insertar.
- Consultar todos los registros.
- Buscar por identificador.
- Actualizar.
- Eliminar.

Las operaciones utilizan funciones suspendidas para evitar ejecutar consultas de base de datos directamente en el hilo principal.

### AppDatabase.kt

Representa la base de datos SQLite administrada mediante Room.

Actualmente está configurada con la entidad `GastoEntity`, la versión 1 y el método `gastoDao()`.

### GastoRepositoryRoom.kt

Es la implementación persistente del contrato `GastoRepository`.

Conecta la lógica de negocio de `gastos-core` con `GastoDao`.

También transforma los objetos `Gasto` en `GastoEntity` y viceversa, permitiendo mantener separada la biblioteca Kotlin del almacenamiento específico de Android.

## 3. Archivos XML

### activity_main.xml

Define la pantalla principal mediante un diseño vertical.

Contiene:

- Título de la pantalla.
- Resumen de gastos.
- Botón para registrar un gasto.
- `RecyclerView` para mostrar los registros.

### activity_formulario_gasto.xml

Define el formulario de registro, edición y eliminación.

Contiene:

- Título del formulario.
- Campo de descripción.
- Campo de monto.
- Lista desplegable de categorías.
- Botón para guardar.
- Botón para eliminar, disponible al editar un gasto existente.

### item_gasto.xml

Define la estructura visual de cada gasto dentro del `RecyclerView`.

Muestra la descripción, la categoría, la fecha y el monto correspondiente.

## 4. Comunicación entre componentes

El funcionamiento general es el siguiente:

1. Android inicia `ControlGastosApp`.
2. La aplicación inicializa la base de datos Room y las dependencias.
3. El usuario abre `MainActivity`.
4. La pantalla consulta los gastos mediante `Dependencias.control`.
5. `ControlGastos` solicita la información al contrato `GastoRepository`.
6. `GastoRepositoryRoom` utiliza `GastoDao` para consultar SQLite.
7. Los registros se convierten en objetos `Gasto`.
8. `GastoAdapter` presenta los resultados en el `RecyclerView`.
9. Si el usuario selecciona un gasto, se abre `FormularioGastoActivity`.
10. El formulario utiliza `ControlGastos` para registrar, actualizar o eliminar información.
11. Room guarda los cambios en la base de datos.
12. Al regresar a la pantalla principal, se actualizan la lista y el resumen.

La persistencia permite conservar los registros cuando se cierra y vuelve a abrir la aplicación.

## 5. Decisiones técnicas

### Separación de responsabilidades

La interfaz Android se encarga de la presentación y de recibir las acciones del usuario.

La biblioteca `gastos-core` administra las validaciones, las reglas de negocio y el contrato de almacenamiento.

Room y SQLite administran la persistencia de los registros.

### ViewBinding

Se utiliza ViewBinding para acceder a los componentes XML mediante referencias generadas automáticamente.

### RecyclerView

Se utiliza RecyclerView para mostrar colecciones de elementos y reutilizar las vistas de sus filas.

### Corrutinas

Se utiliza `lifecycleScope.launch` para ejecutar funciones suspendidas desde las pantallas Android.

### Reutilización del formulario

Se utiliza una misma actividad para registrar y editar gastos, diferenciando el comportamiento mediante el identificador recibido.

### Repositorio compartido

Se centraliza la instancia de `ControlGastos` en `Dependencias`, evitando que cada pantalla cree su propio repositorio.

### SQLite y Room

SQLite permite guardar la información localmente en el dispositivo.

Room facilita el acceso a SQLite mediante entidades, interfaces DAO y una clase de base de datos.

### Patrón Repository

La interfaz `GastoRepository` permite separar las reglas de negocio de los detalles del almacenamiento.

Gracias a este diseño, se sustituyó `GastoRepositoryEnMemoria` por `GastoRepositoryRoom` sin modificar la biblioteca original.

### Evolución de la base de datos

La base de datos utiliza actualmente la versión 1.

Las futuras incorporaciones de ingresos y monedas requerirán estudiar los cambios del esquema y aplicar migraciones de Room para conservar los datos existentes.

## 6. Limitaciones de la versión actual

Aunque el sistema ya cuenta con persistencia y operaciones CRUD, todavía presenta algunas limitaciones:

- La aplicación administra gastos, pero no ingresos.
- No calcula el saldo disponible considerando ingresos y egresos.
- Los montos se presentan únicamente en quetzales.
- No permite registrar movimientos en dólares.
- No almacena tipos de cambio históricos.
- No dispone todavía de estadísticas gráficas completas.
- El diseño visual puede continuar mejorándose.

## 7. Mejoras planificadas

Las siguientes funcionalidades se desarrollarán progresivamente:

1. Registro y administración de ingresos.
2. Cálculo del saldo disponible.
3. Registro de movimientos en quetzales y dólares.
4. Conversión de monedas mediante un tipo de cambio manual.
5. Almacenamiento del tipo de cambio utilizado en cada movimiento.
6. Estadísticas por categoría y resúmenes financieros.
7. Mejoras de diseño y facilidad de uso.
8. Ampliación de las pruebas funcionales y de persistencia.

Cada mejora se implementará, probará y documentará mediante commits independientes en Git.

## 8. Conclusión

La arquitectura de ControlGastos separa la interfaz gráfica, la lógica de negocio y el almacenamiento.

La implementación de Room y SQLite permitió sustituir el almacenamiento temporal por una solución persistente sin modificar el contrato principal de la biblioteca `gastos-core`.

Actualmente, la aplicación permite registrar, consultar, editar y eliminar gastos que permanecen almacenados al cerrar y volver a abrir la aplicación.

La próxima etapa de desarrollo consistirá en implementar ingresos y saldo disponible. Posteriormente, se incorporarán quetzales y dólares, conversiones monetarias y estadísticas financieras.

## Incremento 5: administración de ingresos y saldo disponible

### 1. Objetivo

Ampliar ControlGastos para permitir la administración de ingresos personales y calcular el saldo disponible, conservando la persistencia de datos mediante SQLite y Room.

Esta funcionalidad se incorpora sin modificar las reglas de negocio existentes de la librería `gastos-core`.

### 2. Componentes implementados

**IngresoEntity.kt**

Representa un ingreso almacenado en la base de datos mediante Room.

Contiene los siguientes atributos:

- `id`: identificador del ingreso.
- `descripcion`: concepto del ingreso.
- `monto`: cantidad recibida.
- `fecha`: fecha correspondiente al movimiento.

**IngresoDao.kt**

Define las operaciones necesarias para acceder a los ingresos almacenados en SQLite:

- Consultar todos los ingresos.
- Buscar un ingreso por su identificador.
- Insertar ingresos.
- Actualizar ingresos.
- Eliminar ingresos.

**IngresoRepositoryRoom.kt**

Permite acceder a las operaciones del DAO desde las actividades Android, separando el acceso a datos de la interfaz de usuario.

**AppDatabase.kt**

Se amplió para incluir `IngresoEntity` y proporcionar acceso a `IngresoDao`.

La base de datos pasó a la versión 2, incorporando una nueva tabla para almacenar ingresos.

**Migraciones.kt**

Contiene la migración `MIGRATION_1_2`, que permite incorporar la tabla de ingresos a una base de datos existente sin eliminar la tabla de gastos.

**ControlGastosApp.kt**

Configura la instancia de Room y registra la migración de la versión 1 a la versión 2.

**Dependencias.kt**

Centraliza el acceso compartido a los repositorios de gastos e ingresos.

### 3. Componentes de interfaz

**FormularioIngresoActivity.kt**

Permite registrar, consultar, editar y eliminar ingresos.

Incluye validaciones para la descripción, el monto y la fecha, además de una ventana de confirmación antes de eliminar un registro.

**IngresoAdapter.kt**

Conecta los ingresos almacenados con la lista visual de Android y permite seleccionar un movimiento existente para editarlo.

**MainActivity.kt**

Además de mostrar los gastos, ahora presenta:

- Total de ingresos.
- Total de gastos.
- Saldo disponible.
- Historial de ingresos.
- Botón para registrar nuevos ingresos.

**Archivos XML**

- `activity_formulario_ingreso.xml`: formulario de ingresos.
- `item_ingreso.xml`: representación visual de cada ingreso.
- `activity_main.xml`: pantalla principal con resumen financiero e historiales.

### 4. Cálculo del saldo disponible

La aplicación utiliza la siguiente operación:

**Saldo disponible = Total de ingresos − Total de gastos**

Los ingresos se obtienen mediante el repositorio de ingresos y los gastos mediante la lógica existente de `ControlGastos`.

Al regresar a la pantalla principal, los totales se consultan nuevamente para reflejar los movimientos registrados, modificados o eliminados.

### 5. Persistencia y conservación de datos

Los ingresos y gastos se almacenan mediante Room sobre SQLite.

La migración de la base de datos permite añadir la funcionalidad de ingresos sin eliminar los gastos registrados previamente.

Se comprobó la conservación de los movimientos después de cerrar y volver a abrir la aplicación en un Samsung SM-A165M.

### 6. Próximas mejoras

Las siguientes etapas contemplan:

1. Registro de movimientos en quetzales y dólares.
2. Conversión de monedas mediante tipos de cambio manuales.
3. Conservación del tipo de cambio utilizado en cada movimiento.
4. Estadísticas financieras y mejoras visuales.

La evolución de la base de datos deberá realizarse mediante migraciones para conservar los registros existentes.

## Incremento 6: manejo de quetzales y dólares

### 1. Objetivo

Incorporar el registro y administración de gastos e ingresos en quetzales guatemaltecos (GTQ) y dólares estadounidenses (USD), utilizando un tipo de cambio manual y conservando el valor aplicado a cada movimiento.

### 2. Modificaciones en la base de datos

Se actualizaron las entidades `GastoEntity` e `IngresoEntity` para incorporar los siguientes atributos:

- `moneda`: identifica si el movimiento está registrado en GTQ o USD.
- `tipoCambio`: almacena el tipo de cambio utilizado.

Los movimientos en quetzales utilizan `GTQ` y un tipo de cambio de `1.0`.

Para los movimientos en dólares se guarda el monto original junto con el tipo de cambio ingresado por el usuario.

### 3. Migración de Room

La base de datos pasó de la versión 2 a la versión 3 mediante `MIGRATION_2_3`.

Esta migración agrega los campos `moneda` y `tipoCambio` a las tablas `gastos` e `ingresos`.

Se establecieron valores predeterminados para que los registros anteriores permanezcan almacenados como movimientos en GTQ.

Las migraciones se registraron en `ControlGastosApp.kt`.

### 4. Repositorios

**GastoRepositoryRoom.kt**

Se incorporaron funciones para:

- Registrar gastos con su moneda y tipo de cambio.
- Actualizar gastos conservando o modificando la información monetaria.
- Consultar la moneda y el tipo de cambio de los movimientos.
- Calcular el total de gastos expresado en quetzales.

**IngresoRepositoryRoom.kt**

Se utiliza directamente `IngresoEntity` para registrar y actualizar ingresos con moneda y tipo de cambio.

El cálculo de ingresos se modificó para convertir a quetzales los movimientos registrados en USD.

### 5. Formularios Android

**FormularioGastoActivity.kt**

Permite seleccionar GTQ o USD, ingresar manualmente un tipo de cambio cuando corresponde y mostrar el equivalente en quetzales.

También conserva la moneda y el tipo de cambio al cargar un gasto existente para editarlo.

**FormularioIngresoActivity.kt**

Incorpora el selector de moneda, el cálculo automático del equivalente y el almacenamiento de la moneda y tipo de cambio elegidos.

Al editar un ingreso existente, recupera los valores guardados.

### 6. Historiales y pantalla principal

**GastoAdapter.kt e IngresoAdapter.kt**

Muestran cada movimiento con su moneda original.

Cuando el movimiento está registrado en USD, también presentan su equivalente en quetzales.

**MainActivity.kt**

Presenta los totales de ingresos y gastos expresados en GTQ y calcula el saldo disponible utilizando cantidades convertidas a una misma moneda.

### 7. Reglas de conversión

Para los movimientos en GTQ:

Equivalente en GTQ = Monto original.

Para los movimientos en USD:

Equivalente en GTQ = Monto original × Tipo de cambio.

El saldo disponible se calcula mediante:

Saldo disponible = Total de ingresos en GTQ − Total de gastos en GTQ.

El tipo de cambio se conserva individualmente en cada movimiento, sin depender de un servicio externo.

### 8. Pruebas y resultados

Las pruebas se realizaron en un Samsung SM-A165M.

Se verificaron el registro, edición, eliminación, conversión y persistencia de movimientos en USD.

La actualización de Room conservó los registros existentes.

Los resultados detallados se encuentran en `docs/pruebas-monedas-incremento6.md`.

### 9. Próximas mejoras

Después del Incremento 6, el desarrollo podrá continuar con estadísticas financieras por categoría, resúmenes de movimientos y mejoras en la experiencia de usuario.


## Incremento 7: rediseño de interfaz y estadísticas financieras

### 1. Objetivo

Mejorar la presentación visual de ControlGastos e incorporar estadísticas financieras que permitan conocer la distribución de los gastos por categoría.

### 2. Nuevos componentes

**GastoPorCategoria.kt**

Clase de datos que almacena:

- `categoria`: nombre de la categoría.
- `total`: suma de los gastos de esa categoría expresada en quetzales.

**EstadisticasActivity.kt**

Actividad Android encargada de:

- Consultar los gastos agrupados por categoría.
- Mostrar el total general de gastos.
- Presentar la cantidad de movimientos registrados.
- Calcular el porcentaje correspondiente a cada categoría.
- Generar barras horizontales con colores diferentes.
- Mostrar un mensaje cuando no existen gastos.

**activity_estadisticas.xml**

Define la interfaz visual de estadísticas mediante tarjetas, textos y un contenedor dinámico para las barras de categorías.

### 3. Modificaciones en Room

**GastoDao.kt**

Se incorporó la función `obtenerTotalesPorCategoria()`.

Esta consulta utiliza `SUM`, `CASE WHEN`, `GROUP BY` y `ORDER BY` para agrupar los gastos por categoría, convertir los movimientos USD a GTQ y ordenar los resultados de mayor a menor importe.

**GastoRepositoryRoom.kt**

Se incorporó el método `obtenerGastosPorCategoria()`, que permite acceder a los resultados obtenidos por el DAO.

No fue necesario modificar el esquema de la base de datos ni crear una nueva migración.

### 4. Comunicación entre componentes

El flujo de consulta de estadísticas es:

1. El usuario presiona «Ver estadísticas» en `MainActivity`.
2. Android abre `EstadisticasActivity`.
3. La actividad llama a `Dependencias.gastos.obtenerGastosPorCategoria()`.
4. `GastoRepositoryRoom` solicita los resultados a `GastoDao`.
5. Room consulta SQLite y agrupa los gastos por categoría.
6. La consulta devuelve una lista de objetos `GastoPorCategoria`.
7. `EstadisticasActivity` calcula los porcentajes.
8. La interfaz muestra los importes y las barras de colores.

### 5. Rediseño de interfaz

Se actualizaron los siguientes componentes:

- `activity_main.xml`: tarjetas de saldo y resumen financiero, historiales y botón de estadísticas.
- `activity_formulario_gasto.xml`: nuevo diseño del formulario de gastos.
- `activity_formulario_ingreso.xml`: nuevo diseño del formulario de ingresos.
- `item_gasto.xml`: presentación de gastos con iconos de categoría.
- `item_ingreso.xml`: presentación de ingresos con iconos según moneda.
- `GastoAdapter.kt`: selección de iconos para las categorías.
- `IngresoAdapter.kt`: selección de iconos para ingresos GTQ y USD.

También se incorporaron recursos XML de fondos, tarjetas e iconos.

### 6. Decisiones técnicas

**Consultas mediante Room**

La agrupación de gastos se realiza directamente en SQLite para evitar recorrer todos los registros desde la interfaz.

**Conversión monetaria**

Los movimientos registrados en USD utilizan el tipo de cambio almacenado individualmente.

**Separación de responsabilidades**

El DAO consulta la base de datos, el repositorio proporciona los resultados y la actividad se encarga de presentarlos.

**Componentes nativos**

Las estadísticas utilizan `ProgressBar` horizontal y `TextView`, sin incorporar bibliotecas adicionales de gráficos.

### 7. Resultado

El Incremento 7 incorpora una nueva pantalla de estadísticas financieras y mejora la experiencia visual sin modificar las funcionalidades existentes ni el esquema de Room.

La aplicación conserva el registro de ingresos y gastos, la conversión monetaria y el almacenamiento persistente.
