package umg.edu.gt.controlgastos.datos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        GastoEntity::class,
        IngresoEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gastoDao(): GastoDao

    abstract fun ingresoDao(): IngresoDao
}

