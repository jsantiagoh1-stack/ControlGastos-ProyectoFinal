

\# Diagrama de clases — ControlGastos



\## Arquitectura inicial



Este diagrama representa las clases principales de la

librería gastos-core antes de incorporar Room.



```mermaid

classDiagram

&#x20;   class Gasto {

&#x20;       +Int id

&#x20;       +String descripcion

&#x20;       +Double monto

&#x20;       +Categoria categoria

&#x20;       +String fecha

&#x20;   }



&#x20;   class Categoria {

&#x20;       <<enumeration>>

&#x20;       ALIMENTACION

&#x20;       TRANSPORTE

&#x20;       ESTUDIOS

&#x20;       ENTRETENIMIENTO

&#x20;       SALUD

&#x20;       OTROS

&#x20;   }



&#x20;   class GastoRepository {

&#x20;       <<interface>>

&#x20;       +agregar(gasto) Gasto

&#x20;       +listar() List\~Gasto\~

&#x20;       +buscarPorId(id) Gasto

&#x20;       +actualizar(gasto) Boolean

&#x20;       +eliminar(id) Boolean

&#x20;   }



&#x20;   class GastoRepositoryEnMemoria {

&#x20;       -gastos

&#x20;       -siguienteId

&#x20;       +agregar(gasto) Gasto

&#x20;       +listar() List\~Gasto\~

&#x20;       +buscarPorId(id) Gasto

&#x20;       +actualizar(gasto) Boolean

&#x20;       +eliminar(id) Boolean

&#x20;       -buscarPosicion(id) Int

&#x20;   }



&#x20;   class ControlGastos {

&#x20;       -repositorio

&#x20;       +validar(gasto) String

&#x20;       +registrar(gasto) String

&#x20;       +actualizar(gasto) String

&#x20;       +listar() List\~Gasto\~

&#x20;       +buscarPorId(id) Gasto

&#x20;       +eliminar(id) Boolean

&#x20;       +total() Double

&#x20;       +totalPorCategoria() Map

&#x20;       +resumen() String

&#x20;   }



&#x20;   Gasto --> Categoria : utiliza

&#x20;   GastoRepository ..> Gasto : administra

&#x20;   GastoRepositoryEnMemoria ..|> GastoRepository : implementa

&#x20;   GastoRepositoryEnMemoria o-- Gasto : almacena

&#x20;   ControlGastos --> GastoRepository : utiliza

```



\## Explicación de las relaciones



\- ControlGastos utiliza la interfaz GastoRepository.

\- GastoRepository define las operaciones CRUD.

\- GastoRepositoryEnMemoria implementa ese contrato.

\- Gasto representa la información de un gasto.

\- Categoria define las categorías disponibles.



\## Decisión de diseño



La separación entre el servicio y el repositorio permite

reemplazar el almacenamiento temporal por Room sin

reescribir la lógica principal del sistema.



