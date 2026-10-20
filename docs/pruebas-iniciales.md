\# Pruebas de la versión inicial — ControlGastos



\## Objetivo



Comprobar el funcionamiento de la aplicación original antes de incorporar la persistencia de datos mediante Room y SQLite.



\## Pruebas realizadas



| Prueba | Resultado |

|---|---|

| Compilación de la librería gastos-core | Correcta: BUILD SUCCESS |

| Compilación del proyecto Android | Correcta: BUILD SUCCESSFUL |

| Ejecución en Samsung SM-A165M | Correcta |

| Registro de gasto | Correcto |

| Edición de gasto | Realizada durante las pruebas |

| Conservación de datos al reiniciar la aplicación | No conserva los registros al finalizar el proceso |



\## Prueba de persistencia



Se registró un gasto llamado \*\*Almuerzo\*\*, con un monto de Q35.50.



Posteriormente se realizó una prueba de edición y se cerró la aplicación.



Al volver a abrirla, el sistema mostró:



\*\*0 gastos · Total: Q 0.00\*\*



El registro anterior ya no aparecía.



\## Análisis del resultado



El almacenamiento original utiliza `GastoRepositoryEnMemoria`, que administra los gastos mediante una lista mutable.



Los datos permanecen disponibles durante la ejecución del proceso, pero no se guardan en una base de datos permanente.



\## Decisión técnica



Se implementará Room sobre SQLite para permitir que los gastos registrados, editados o eliminados mantengan su estado incluso después de cerrar y volver a abrir la aplicación.



La implementación deberá respetar el contrato `GastoRepository` y conservar las reglas de negocio de la librería `gastos-core`.



\## Estado



Pruebas iniciales realizadas. Pendiente implementar y verificar persistencia con Room.

