package umg.edu.gt.controlgastos.datos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface IngresoDao {

    @Query("SELECT * FROM ingresos ORDER BY fecha DESC")
    suspend fun obtenerTodos(): List<IngresoEntity>

    @Query("SELECT * FROM ingresos WHERE id = :id")
    suspend fun obtenerPorId(id: Int): IngresoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(ingreso: IngresoEntity): Long

    @Update
    suspend fun actualizar(ingreso: IngresoEntity)

    @Delete
    suspend fun eliminar(ingreso: IngresoEntity)
}
