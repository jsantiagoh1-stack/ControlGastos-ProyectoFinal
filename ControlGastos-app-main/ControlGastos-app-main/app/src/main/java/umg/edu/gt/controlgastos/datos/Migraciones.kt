package umg.edu.gt.controlgastos.datos

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `ingresos` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `descripcion` TEXT NOT NULL,
                `monto` REAL NOT NULL,
                `fecha` TEXT NOT NULL
            )
            """.trimIndent()
        )
    }
}