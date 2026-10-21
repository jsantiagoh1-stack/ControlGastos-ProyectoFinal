package umg.edu.gt.controlgastos

import edu.umg.programacion2.gastos.servicio.ControlGastos
import umg.edu.gt.controlgastos.datos.GastoRepositoryRoom
import umg.edu.gt.controlgastos.datos.IngresoRepositoryRoom

object Dependencias {

    lateinit var control: ControlGastos
        private set

    lateinit var ingresos: IngresoRepositoryRoom
        private set

    fun inicializar(app: ControlGastosApp) {

        val repositorioGastos = GastoRepositoryRoom(
            app.database.gastoDao()
        )

        control = ControlGastos(repositorioGastos)

        ingresos = IngresoRepositoryRoom(
            app.database.ingresoDao()
        )
    }
}