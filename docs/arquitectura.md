\# Arquitectura del proyecto ControlGastos



\## 1. Introducción



El proyecto ControlGastos utiliza una estructura que separa los modelos de datos, la lógica de negocio y el almacenamiento de información.



Esta organización permite mantener el código ordenado, facilitar su mantenimiento y agregar nuevas funcionalidades sin modificar innecesariamente las clases existentes.



\## 2. Estructura de la librería



La librería `gastos-core` está desarrollada en Kotlin y contiene los siguientes componentes principales:



\### Gasto.kt



Es una clase de datos (`data class`) que representa un gasto registrado por el usuario.



Contiene los atributos:



\- `id`: identificador del gasto.

\- `descripcion`: nombre o detalle.

\- `monto`: cantidad de dinero.

\- `categoria`: categoría a la que pertenece.

\- `fecha`: fecha del gasto.



Se utiliza una `data class` para facilitar el manejo de los objetos y evitar escribir manualmente métodos comunes.



\### Categoria.kt



Es una enumeración (`enum class`) que define las categorías disponibles para clasificar los gastos.



Incluye alimentación, transporte, estudios, entretenimiento, salud y otros.



Se utiliza una enumeración para limitar los valores disponibles y evitar categorías incorrectas.



\### GastoRepository.kt



Es una interfaz que establece las operaciones principales del almacenamiento:



\- Agregar gastos.

\- Listar gastos.

\- Buscar gastos por identificador.

\- Actualizar gastos.

\- Eliminar gastos.



La interfaz permite cambiar la implementación del almacenamiento sin alterar el contrato que utiliza la lógica del sistema.



\### GastoRepositoryEnMemoria.kt



Es la implementación inicial de `GastoRepository`.



Utiliza una lista mutable para almacenar temporalmente los gastos y proporciona las operaciones CRUD.



Su principal limitación es que los datos desaparecen cuando termina el proceso de la aplicación.



\### ControlGastos.kt



Contiene la lógica de negocio del sistema.



Sus responsabilidades incluyen:



\- Validar los datos de los gastos.

\- Registrar y actualizar movimientos.

\- Buscar, listar y eliminar gastos.

\- Calcular el total de gastos.

\- Calcular totales por categoría.

\- Generar un resumen financiero.



Esta clase recibe una implementación de `GastoRepository` mediante su constructor.



\## 3. Decisiones de diseño



\*\*Separación de responsabilidades:\*\* los modelos representan la información, el repositorio administra el almacenamiento y el servicio contiene las reglas de negocio.



\*\*Uso de interfaces:\*\* permite intercambiar implementaciones de almacenamiento manteniendo el mismo contrato.



\*\*Encapsulamiento:\*\* las propiedades privadas protegen los datos internos de las clases.



\*\*Uso de corrutinas:\*\* los métodos `suspend` preparan las operaciones para trabajar con almacenamiento que puede requerir tiempo de espera.



\*\*Reutilización del código:\*\* la librería es independiente de Android y puede utilizarse tanto desde consola como desde una aplicación móvil.



\## 4. Estado inicial



La versión base almacena la información mediante `GastoRepositoryEnMemoria`, por lo que no dispone de persistencia permanente.



La aplicación Android utiliza esta librería para realizar operaciones sobre los gastos.



\## 5. Mejoras previstas



Durante el desarrollo del proyecto final se evaluará la incorporación de:



\- Persistencia con Room y SQLite.

\- Administración de ingresos.

\- Conversión manual entre quetzales y dólares.

\- Conservación de tipos de cambio históricos.

\- Estadísticas financieras.

\- Mejoras en la interfaz de usuario.



Estas funcionalidades se implementarán progresivamente y se documentarán cuando estén terminadas.

