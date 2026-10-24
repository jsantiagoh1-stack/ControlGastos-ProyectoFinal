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

val MIGRATION_2_3 = object : Migration(2, 3) {

    override fun migrate(connection: SQLiteConnection) {

        // Agregar moneda y tipo de cambio a gastos
        connection.execSQL(
            "ALTER TABLE `gastos` ADD COLUMN `moneda` TEXT NOT NULL DEFAULT 'GTQ'"
        )

        connection.execSQL(
            "ALTER TABLE `gastos` ADD COLUMN `tipoCambio` REAL NOT NULL DEFAULT 1.0"
        )

        // Agregar moneda y tipo de cambio a ingresos
        connection.execSQL(
            "ALTER TABLE `ingresos` ADD COLUMN `moneda` TEXT NOT NULL DEFAULT 'GTQ'"
        )

        connection.execSQL(
            "ALTER TABLE `ingresos` ADD COLUMN `tipoCambio` REAL NOT NULL DEFAULT 1.0"
        )
    }
}
