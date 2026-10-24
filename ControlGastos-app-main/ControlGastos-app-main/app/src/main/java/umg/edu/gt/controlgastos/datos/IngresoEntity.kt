package umg.edu.gt.controlgastos.datos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingresos")
data class IngresoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val descripcion: String,
    val monto: Double,
    val fecha: String,

    @ColumnInfo(defaultValue = "'GTQ'")
    val moneda: String = "GTQ",

    @ColumnInfo(defaultValue = "1.0")
    val tipoCambio: Double = 1.0
)
