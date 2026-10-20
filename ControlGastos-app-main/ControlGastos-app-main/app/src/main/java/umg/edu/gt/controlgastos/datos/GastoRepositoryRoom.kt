package umg.edu.gt.controlgastos.datos

import edu.umg.programacion2.gastos.datos.GastoRepository
import edu.umg.programacion2.gastos.modelo.Categoria
import edu.umg.programacion2.gastos.modelo.Gasto

class GastoRepositoryRoom(
    private val dao: GastoDao
) : GastoRepository {

    override suspend fun agregar(gasto: Gasto): Gasto {
        val entidad = gasto.toEntity()
        val nuevoId = dao.insertar(entidad)

        return gasto.copy(id = nuevoId.toInt())
    }

    override suspend fun listar(): List<Gasto> {
        return dao.obtenerTodos().map { it.toGasto() }
    }

    override suspend fun buscarPorId(id: Int): Gasto? {
        return dao.obtenerPorId(id)?.toGasto()
    }

    override suspend fun actualizar(gasto: Gasto): Boolean {
        val existente = dao.obtenerPorId(gasto.id) ?: return false

        dao.actualizar(gasto.toEntity())
        return true
    }

    override suspend fun eliminar(id: Int): Boolean {
        val existente = dao.obtenerPorId(id) ?: return false

        dao.eliminar(existente)
        return true
    }

    private fun Gasto.toEntity(): GastoEntity {
        return GastoEntity(
            id = id,
            descripcion = descripcion,
            monto = monto,
            categoria = categoria.name,
            fecha = fecha
        )
    }

    private fun GastoEntity.toGasto(): Gasto {
        return Gasto(
            id = id,
            descripcion = descripcion,
            monto = monto,
            categoria = Categoria.valueOf(categoria),
            fecha = fecha
        )
    }
}
