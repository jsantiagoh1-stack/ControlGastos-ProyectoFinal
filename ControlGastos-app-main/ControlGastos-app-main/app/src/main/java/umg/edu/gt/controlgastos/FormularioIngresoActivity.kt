package umg.edu.gt.controlgastos

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import umg.edu.gt.controlgastos.datos.IngresoEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.widget.TextView

class FormularioIngresoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_INGRESO = "id_ingreso"
    }

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

        val eliminar = findViewById<Button>(
            R.id.btnEliminarIngreso
        )

        val formatoFecha = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        ).apply {
            isLenient = false
        }

        // id = -1 significa que se creará un ingreso nuevo.
        val idIngreso = intent.getIntExtra(
            EXTRA_ID_INGRESO,
            -1
        )

        val modoEdicion = idIngreso != -1
        val titulo = findViewById<TextView>(R.id.txtTituloIngreso)

        titulo.text = if (modoEdicion) {
            "Editar ingreso"
        } else {
            "Registrar ingreso"
        }

        fecha.setText(formatoFecha.format(Date()))

        // Cargar los datos del ingreso existente.
        if (modoEdicion) {

            guardar.text = "Actualizar ingreso"
            eliminar.visibility = View.VISIBLE

            guardar.isEnabled = false
            eliminar.isEnabled = false

            lifecycleScope.launch {
                try {
                    val ingreso = Dependencias.ingresos
                        .buscarPorId(idIngreso)

                    if (ingreso == null) {
                        Toast.makeText(
                            this@FormularioIngresoActivity,
                            "Ingreso no encontrado",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                        return@launch
                    }

                    descripcion.setText(ingreso.descripcion)
                    monto.setText(ingreso.monto.toString())
                    fecha.setText(ingreso.fecha)

                    guardar.isEnabled = true
                    eliminar.isEnabled = true

                } catch (e: Exception) {
                    Toast.makeText(
                        this@FormularioIngresoActivity,
                        "No se pudo cargar el ingreso",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                }
            }
        }

        // Registrar o actualizar un ingreso.
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

            if (
                valorMonto == null ||
                !valorMonto.isFinite() ||
                valorMonto <= 0.0
            ) {
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

            lifecycleScope.launch {
                try {
                    val ingreso = IngresoEntity(
                        id = if (modoEdicion) idIngreso else 0,
                        descripcion = textoDescripcion,
                        monto = valorMonto,
                        fecha = textoFecha
                    )

                    if (modoEdicion) {
                        Dependencias.ingresos.actualizar(ingreso)
                    } else {
                        Dependencias.ingresos.agregar(ingreso)
                    }

                    val mensaje = if (modoEdicion) {
                        "Ingreso actualizado correctamente"
                    } else {
                        "Ingreso guardado correctamente"
                    }

                    Toast.makeText(
                        this@FormularioIngresoActivity,
                        mensaje,
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

        // Eliminar con confirmación.
        eliminar.setOnClickListener {

            if (!modoEdicion) {
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Eliminar ingreso")
                .setMessage(
                    "¿Estás seguro de eliminar este ingreso? " +
                            "Esta acción no se puede deshacer."
                )
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Eliminar") { _, _ ->

                    eliminar.isEnabled = false
                    guardar.isEnabled = false

                    lifecycleScope.launch {
                        try {
                            Dependencias.ingresos.eliminar(idIngreso)

                            Toast.makeText(
                                this@FormularioIngresoActivity,
                                "Ingreso eliminado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()

                        } catch (e: Exception) {
                            eliminar.isEnabled = true
                            guardar.isEnabled = true

                            Toast.makeText(
                                this@FormularioIngresoActivity,
                                "No se pudo eliminar el ingreso",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                .show()
        }
    }
}
