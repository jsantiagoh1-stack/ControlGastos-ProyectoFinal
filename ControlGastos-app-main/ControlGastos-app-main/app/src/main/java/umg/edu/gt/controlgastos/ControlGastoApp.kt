package umg.edu.gt.controlgastos

import android.app.Application
import androidx.room.Room
import umg.edu.gt.controlgastos.datos.AppDatabase
import umg.edu.gt.controlgastos.datos.MIGRATION_1_2
import umg.edu.gt.controlgastos.datos.MIGRATION_2_3

class ControlGastosApp : Application() {

    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "control_gastos.db"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Dependencias.inicializar(this)
    }
}
