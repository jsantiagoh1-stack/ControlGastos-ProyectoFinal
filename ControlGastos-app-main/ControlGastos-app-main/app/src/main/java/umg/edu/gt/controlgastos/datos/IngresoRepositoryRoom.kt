package umg.edu.gt.controlgastos.datos

class IngresoRepositoryRoom(
    private val dao: IngresoDao
) {

    suspend fun agregar(ingreso: IngresoEntity): IngresoEntity {
        val nuevoId = dao.insertar(ingreso)
        return ingreso.copy(id = nuevoId.toInt())
    }

    suspend fun listar(): List<IngresoEntity> {
        return dao.obtenerTodos()
    }

    suspend fun buscarPorId(id: Int): IngresoEntity? {
        return dao.obtenerPorId(id)
    }

    suspend fun actualizar(ingreso: IngresoEntity): Boolean {
        dao.obtenerPorId(ingreso.id) ?: return false

        dao.actualizar(ingreso)
        return true
    }

    suspend fun eliminar(id: Int): Boolean {
        val existente = dao.obtenerPorId(id) ?: return false

        dao.eliminar(existente)
        return true
    }

    suspend fun totalIngresos(): Double {

        val ingresos = dao.obtenerTodos()

        return ingresos.sumOf { ingreso ->

            if (ingreso.moneda == "USD") {
                ingreso.monto * ingreso.tipoCambio
            } else {
                ingreso.monto
            }
        }
    }
}
