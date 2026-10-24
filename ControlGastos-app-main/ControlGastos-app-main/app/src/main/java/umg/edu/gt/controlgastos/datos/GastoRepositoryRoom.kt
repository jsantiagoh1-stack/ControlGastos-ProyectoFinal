package umg.edu.gt.controlgastos.datos

import edu.umg.programacion2.gastos.datos.GastoRepository
import edu.umg.programacion2.gastos.modelo.Categoria
import edu.umg.programacion2.gastos.modelo.Gasto

class GastoRepositoryRoom(
    private val dao: GastoDao
) : GastoRepository {

    suspend fun agregarConMoneda(
        gasto: Gasto,
        moneda: String,
        tipoCambio: Double
    ): Gasto {
        require(moneda == "GTQ" || moneda == "USD")
        require(tipoCambio.isFinite() && tipoCambio > 0.0)

        val entidad = gasto.toEntity().copy(
            moneda = moneda,
            tipoCambio = if (moneda == "GTQ") 1.0 else tipoCambio
        )

        val nuevoId = dao.insertar(entidad)
        return gasto.copy(id = nuevoId.toInt())
    }

    // Actualizar un gasto, incluyendo moneda y tipo de cambio.
    suspend fun actualizarConMoneda(
        gasto: Gasto,
        moneda: String,
        tipoCambio: Double
    ): Boolean {
        val existente = dao.obtenerPorId(gasto.id) ?: return false

        require(moneda == "GTQ" || moneda == "USD")
        require(tipoCambio.isFinite() && tipoCambio > 0.0)

        val actualizado = existente.copy(
            descripcion = gasto.descripcion,
            monto = gasto.monto,
            categoria = gasto.categoria.name,
            fecha = gasto.fecha,
            moneda = moneda,
            tipoCambio = if (moneda == "GTQ") 1.0 else tipoCambio
        )

        dao.actualizar(actualizado)
        return true
    }


    override suspend fun agregar(gasto: Gasto): Gasto {
        val entidad = gasto.toEntity()
        val nuevoId = dao.insertar(entidad)

        return gasto.copy(id = nuevoId.toInt())
    }

    override suspend fun listar(): List<Gasto> {
        return dao.obtenerTodos().map { it.toGasto() }
    }

    suspend fun totalGastosEnQuetzales(): Double {

        val gastos = dao.obtenerTodos()

        return gastos.sumOf { gasto ->

            if (gasto.moneda == "USD") {
                gasto.monto * gasto.tipoCambio
            } else {
                gasto.monto
            }
        }
    }

    override suspend fun buscarPorId(id: Int): Gasto? {
        return dao.obtenerPorId(id)?.toGasto()
    }

    suspend fun obtenerMonedaYTipoCambio(
        id: Int
    ): Pair<String, Double>? {
        val entidad = dao.obtenerPorId(id) ?: return null

        return Pair(
            entidad.moneda,
            entidad.tipoCambio
        )
    }

    suspend fun obtenerMonedasPorGasto(): Map<Int, Pair<String, Double>> {

        val gastos = dao.obtenerTodos()

        return gastos.associate { gasto ->
            gasto.id to Pair(
                gasto.moneda,
                gasto.tipoCambio
            )
        }
    }


    override suspend fun actualizar(gasto: Gasto): Boolean {
        val existente = dao.obtenerPorId(gasto.id) ?: return false

        val actualizado = existente.copy(
            descripcion = gasto.descripcion,
            monto = gasto.monto,
            categoria = gasto.categoria.name,
            fecha = gasto.fecha
        )

        dao.actualizar(actualizado)
        return true
    }


    override suspend fun eliminar(id: Int): Boolean {
        val existente = dao.obtenerPorId(id) ?: return false

        dao.eliminar(existente)
        return true
    }

    suspend fun obtenerGastosPorCategoria(): List<GastoPorCategoria> {
        return dao.obtenerTotalesPorCategoria()
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
