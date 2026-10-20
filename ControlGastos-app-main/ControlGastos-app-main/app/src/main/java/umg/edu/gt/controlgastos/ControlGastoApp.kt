package umg.edu.gt.controlgastos

import android.app.Application
import androidx.room.Room
import umg.edu.gt.controlgastos.datos.AppDatabase

class ControlGastosApp : Application() {

    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "control_gastos.db"
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
        Dependencias.inicializar(this)
    }
}
