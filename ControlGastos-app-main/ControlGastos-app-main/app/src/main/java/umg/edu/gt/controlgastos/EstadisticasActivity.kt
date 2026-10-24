package umg.edu.gt.controlgastos

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

class EstadisticasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_estadisticas)
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            cargarEstadisticas()
        }
    }

    private suspend fun cargarEstadisticas() {

        val tvTotal = findViewById<TextView>(
            R.id.tvEstadisticasTotal
        )

        val tvCantidad = findViewById<TextView>(
            R.id.tvEstadisticasCantidad
        )

        val tvSinDatos = findViewById<TextView>(
            R.id.tvSinDatos
        )

        val contenedor = findViewById<LinearLayout>(
            R.id.contenedorCategorias
        )

        contenedor.removeAllViews()

        try {

            // Consultar las estadísticas desde Room.
            val categorias = Dependencias.gastos
                .obtenerGastosPorCategoria()

            // Obtener la cantidad real de gastos.
            val cantidadGastos = Dependencias.control
                .listar().size

            // Los totales ya vienen convertidos a GTQ.
            val total = categorias.sumOf { it.total }

            tvTotal.text = String.format(
                Locale.US,
                "Q %,.2f",
                total
            )

            tvCantidad.text =
                "$cantidadGastos movimientos registrados"

            if (categorias.isEmpty() || total <= 0.0) {

                tvSinDatos.text =
                    "Todavía no hay gastos para mostrar."

                tvSinDatos.visibility = View.VISIBLE
                return
            }

            tvSinDatos.visibility = View.GONE

            for (categoria in categorias) {

                val porcentaje = categoria.total / total * 100.0

                val colorCategoria = when (
                    categoria.categoria.uppercase(Locale.ROOT)
                ) {
                    "ALIMENTACION" -> "#E49A39"
                    "TRANSPORTE" -> "#16834F"
                    "ESTUDIOS" -> "#3578C7"
                    "ENTRETENIMIENTO" -> "#895DC5"
                    "SALUD" -> "#DB5374"
                    "OTROS" -> "#657888"
                    else -> "#167A4A"
                }

                val nombreCategoria = categoria.categoria
                    .lowercase(Locale.getDefault())
                    .replaceFirstChar {
                        it.titlecase(Locale.getDefault())
                    }

                val texto = TextView(this).apply {

                    text = String.format(
                        Locale.US,
                        "%s · Q %,.2f (%.1f%%)",
                        nombreCategoria,
                        categoria.total,
                        porcentaje
                    )

                    textSize = 14f
                    setTextColor(Color.parseColor("#172820"))
                }

                val barra = ProgressBar(
                    this,
                    null,
                    android.R.attr.progressBarStyleHorizontal
                ).apply {

                    max = 1000

                    progress = (
                            porcentaje * 10.0
                            ).roundToInt().coerceIn(0, 1000)

                    progressTintList = ColorStateList.valueOf(
                        Color.parseColor(colorCategoria)
                    )

                    progressBackgroundTintList =
                        ColorStateList.valueOf(
                            Color.parseColor("#E8EFEB")
                        )
                }

                val margenTexto = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 20.dp()
                }

                val margenBarra = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    12.dp()
                ).apply {
                    topMargin = 8.dp()
                }

                contenedor.addView(texto, margenTexto)
                contenedor.addView(barra, margenBarra)
            }

        } catch (e: Exception) {

            tvSinDatos.text =
                "No se pudieron cargar las estadísticas."

            tvSinDatos.visibility = View.VISIBLE

            Toast.makeText(
                this,
                "Error al consultar los gastos",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density)
            .roundToInt()
    }
}
