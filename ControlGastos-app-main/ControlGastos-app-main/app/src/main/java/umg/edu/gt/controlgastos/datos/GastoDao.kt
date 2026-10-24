package umg.edu.gt.controlgastos.datos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GastoDao {

    @Query("SELECT * FROM gastos ORDER BY fecha DESC")
    suspend fun obtenerTodos(): List<GastoEntity>

    @Query("""
    SELECT
        categoria,
        SUM(
            CASE
                WHEN moneda = 'USD'
                THEN monto * tipoCambio
                ELSE monto
            END
        ) AS total
    FROM gastos
    GROUP BY categoria
    ORDER BY total DESC
""")
    suspend fun obtenerTotalesPorCategoria(): List<GastoPorCategoria>

    @Query("SELECT * FROM gastos WHERE id = :id")
    suspend fun obtenerPorId(id: Int): GastoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(gasto: GastoEntity): Long

    @Update
    suspend fun actualizar(gasto: GastoEntity)

    @Delete
    suspend fun eliminar(gasto: GastoEntity)
}
