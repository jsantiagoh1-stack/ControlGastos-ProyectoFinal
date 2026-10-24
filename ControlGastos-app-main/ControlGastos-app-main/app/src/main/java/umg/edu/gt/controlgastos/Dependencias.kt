package umg.edu.gt.controlgastos

import edu.umg.programacion2.gastos.servicio.ControlGastos
import umg.edu.gt.controlgastos.datos.GastoRepositoryRoom
import umg.edu.gt.controlgastos.datos.IngresoRepositoryRoom

object Dependencias {

    lateinit var control: ControlGastos
        private set

    lateinit var gastos: GastoRepositoryRoom
        private set

    lateinit var ingresos: IngresoRepositoryRoom
        private set

    fun inicializar(app: ControlGastosApp) {

        gastos = GastoRepositoryRoom(
            app.database.gastoDao()
        )

        control = ControlGastos(gastos)

        ingresos = IngresoRepositoryRoom(
            app.database.ingresoDao()
        )
    }
}
