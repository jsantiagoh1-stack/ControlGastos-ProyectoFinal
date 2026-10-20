
package umg.edu.gt.controlgastos

import edu.umg.programacion2.gastos.servicio.ControlGastos
import umg.edu.gt.controlgastos.datos.GastoRepositoryRoom

object Dependencias {

    lateinit var control: ControlGastos
        private set

    fun inicializar(app: ControlGastosApp) {
        val repositorio = GastoRepositoryRoom(
            app.database.gastoDao()
        )

        control = ControlGastos(repositorio)
    }
}
