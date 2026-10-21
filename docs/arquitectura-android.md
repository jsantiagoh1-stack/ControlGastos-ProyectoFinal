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
