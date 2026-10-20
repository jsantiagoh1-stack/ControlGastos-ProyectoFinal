\# Arquitectura Android — ControlGastos



\## 1. Introducción



ControlGastos es una aplicación Android desarrollada en Kotlin que permite registrar, consultar y editar gastos personales.



La aplicación utiliza una librería independiente llamada `gastos-core`, encargada de los modelos de datos, las validaciones y las reglas de negocio.



En esta etapa inicial, los gastos se almacenan temporalmente en memoria. La persistencia mediante Room y SQLite se implementará en una etapa posterior.



\## 2. Organización de la aplicación



El proyecto Android está organizado mediante clases Kotlin y archivos XML.



Las clases Kotlin controlan el comportamiento de las pantallas y los archivos XML definen su estructura visual.



\### MainActivity.kt



Es la pantalla principal de la aplicación.



Sus responsabilidades son:



\- Mostrar la lista de gastos registrados.

\- Presentar la cantidad de gastos y el total acumulado.

\- Abrir el formulario para crear nuevos gastos.

\- Abrir el formulario para editar gastos existentes.

\- Actualizar la información al regresar del formulario.



Utiliza `RecyclerView` para presentar los registros y `lifecycleScope` para ejecutar operaciones suspendidas.



\### FormularioGastoActivity.kt



Controla el formulario utilizado para registrar y editar gastos.



Tiene dos modos de funcionamiento:



\*\*Modo crear:\*\* permite introducir la descripción, el monto y la categoría de un gasto nuevo.



\*\*Modo editar:\*\* busca el gasto mediante su identificador y carga sus datos para modificarlos.



Utiliza `ControlGastos` para validar y guardar la información, evitando duplicar las reglas de negocio dentro de la interfaz.



\### GastoAdapter.kt



Es el adaptador encargado de conectar los objetos `Gasto` con las filas del `RecyclerView`.



Sus principales responsabilidades son:



\- Crear y reutilizar las vistas de cada fila.

\- Mostrar la descripción, categoría, fecha y monto.

\- Actualizar los elementos de la lista.

\- Notificar a `MainActivity` cuando el usuario selecciona un gasto.



\### Dependencias.kt



Centraliza la creación de la instancia compartida de `ControlGastos`.



En la versión inicial utiliza:



`ControlGastos(GastoRepositoryEnMemoria())`



Esta decisión permite que `MainActivity` y `FormularioGastoActivity` trabajen con el mismo repositorio durante la ejecución de la aplicación.



\## 3. Archivos XML



\### activity\_main.xml



Define la pantalla principal mediante un `LinearLayout` vertical.



Contiene:



\- Título de la pantalla.

\- Resumen de gastos.

\- Botón para registrar un gasto.

\- `RecyclerView` para mostrar los registros.



\### activity\_formulario\_gasto.xml



Define el formulario de registro y edición.



Contiene:



\- Título del formulario.

\- Campo de descripción.

\- Campo de monto.

\- Lista desplegable de categorías.

\- Botón para guardar.



\### item\_gasto.xml



Define la estructura visual de cada gasto dentro del `RecyclerView`.



Muestra la descripción, la categoría, la fecha y el monto correspondiente.



\## 4. Comunicación entre componentes



El funcionamiento general es el siguiente:



1\. El usuario abre `MainActivity`.

2\. La pantalla consulta los gastos mediante `Dependencias.control`.

3\. `ControlGastos` solicita la información a `GastoRepository`.

4\. `GastoRepositoryEnMemoria` devuelve los gastos almacenados.

5\. `GastoAdapter` presenta los resultados en el `RecyclerView`.

6\. Si el usuario selecciona un gasto, se abre `FormularioGastoActivity`.

7\. El formulario utiliza `ControlGastos` para registrar o actualizar la información.

8\. Al regresar a la pantalla principal, se actualizan la lista y el resumen.



\## 5. Decisiones técnicas



\### Separación de responsabilidades



La interfaz Android se encarga de la presentación y de recibir las acciones del usuario.



La librería `gastos-core` administra las validaciones, las reglas de negocio y el contrato de almacenamiento.



\### ViewBinding



Se utiliza ViewBinding para acceder a los componentes XML mediante referencias generadas automáticamente, evitando búsquedas manuales de vistas.



\### RecyclerView



Se utiliza RecyclerView porque permite mostrar colecciones de elementos y reutilizar las vistas de sus filas.



\### Corrutinas



Se utiliza `lifecycleScope.launch` para llamar a las funciones suspendidas de la lógica de negocio desde las pantallas Android.



\### Reutilización del formulario



Se utiliza una misma actividad para registrar y editar gastos, diferenciando el comportamiento mediante el identificador recibido.



\### Repositorio compartido



Se centraliza la instancia de `ControlGastos` en `Dependencias`, evitando que cada pantalla cree su propio repositorio temporal.



\## 6. Limitaciones de la versión inicial



\- Los datos no se conservan cuando termina el proceso de la aplicación.

\- No existe todavía una opción de eliminación desde el formulario Android.

\- La aplicación administra gastos, pero no ingresos.

\- Los montos se presentan únicamente en quetzales.

\- No existen todavía estadísticas gráficas.



\## 7. Mejoras planificadas



Las siguientes funcionalidades se desarrollarán progresivamente:



1\. Persistencia de datos mediante Room y SQLite.

2\. Eliminación de gastos con confirmación.

3\. Mejoras de diseño y facilidad de uso.

4\. Registro de ingresos y consulta del saldo.

5\. Conversión manual entre quetzales y dólares.

6\. Almacenamiento histórico del tipo de cambio utilizado.

7\. Estadísticas y resúmenes financieros.



Cada mejora se implementará, probará y documentará mediante commits independientes en Git.



\## 8. Conclusión



La arquitectura inicial de ControlGastos separa la interfaz gráfica, la lógica de negocio y el almacenamiento.



Esta organización facilita el mantenimiento y permite incorporar nuevas funcionalidades sin reescribir completamente la aplicación.



El siguiente incremento técnico consistirá en sustituir el almacenamiento temporal por una implementación persistente utilizando Room y SQLite.

