package umg.edu.gt.controlgastos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import umg.edu.gt.controlgastos.datos.IngresoEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormularioIngresoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_ingreso)

        val descripcion = findViewById<EditText>(
            R.id.etDescripcionIngreso
        )

        val monto = findViewById<EditText>(
            R.id.etMontoIngreso
        )

        val fecha = findViewById<EditText>(
            R.id.etFechaIngreso
        )

        val guardar = findViewById<Button>(
            R.id.btnGuardarIngreso
        )

        val formatoFecha = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        ).apply {
            isLenient = false
        }

        fecha.setText(formatoFecha.format(Date()))

        guardar.setOnClickListener {

            val textoDescripcion =
                descripcion.text.toString().trim()

            val valorMonto =
                monto.text.toString().trim().toDoubleOrNull()

            val textoFecha =
                fecha.text.toString().trim()

            if (textoDescripcion.isEmpty()) {
                descripcion.error = "Ingresa una descripción"
                return@setOnClickListener
            }

            if (valorMonto == null ||
                !valorMonto.isFinite() ||
                valorMonto <= 0.0) {

                monto.error = "Ingresa un monto válido"
                return@setOnClickListener
            }

            val fechaValida = try {
                formatoFecha.parse(textoFecha)?.let {
                    formatoFecha.format(it) == textoFecha
                } ?: false
            } catch (e: Exception) {
                false
            }

            if (!fechaValida) {
                fecha.error = "Utiliza AAAA-MM-DD"
                return@setOnClickListener
            }

            guardar.isEnabled = false

            val ingreso = IngresoEntity(
                descripcion = textoDescripcion,
                monto = valorMonto,
                fecha = textoFecha
            )

            lifecycleScope.launch {
                try {
                    Dependencias.ingresos.agregar(ingreso)

                    Toast.makeText(
                        this@FormularioIngresoActivity,
                        "Ingreso guardado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()

                } catch (e: Exception) {
                    guardar.isEnabled = true

                    Toast.makeText(
                        this@FormularioIngresoActivity,
                        "No se pudo guardar el ingreso",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}