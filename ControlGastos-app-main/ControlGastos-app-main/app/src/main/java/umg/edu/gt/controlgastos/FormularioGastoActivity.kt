package umg.edu.gt.controlgastos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import edu.umg.programacion2.gastos.modelo.Categoria
import edu.umg.programacion2.gastos.modelo.Gasto
import kotlinx.coroutines.launch
import umg.edu.gt.controlgastos.databinding.ActivityFormularioGastoBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.view.View
import androidx.appcompat.app.AlertDialog
import android.widget.AdapterView
import android.text.Editable
import android.text.TextWatcher

/**
 * Pantalla de formulario. Tiene DOS modos:
 *  - CREAR:  se abre sin extra (boton "+" de la lista).
 *  - EDITAR: se abre con el extra EXTRA_ID_GASTO (al tocar un gasto de la lista).
 */
class FormularioGastoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormularioGastoBinding

    // IMPORTANTE: el MISMO control que usa la lista (ver Dependencias.kt).
    private val control = Dependencias.control

    // null = modo CREAR. Si tiene un Gasto = modo EDITAR (es el gasto original).
    private var gastoEditando: Gasto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Inflar el layout con View Binding (igual que en la Clase 12).
        binding = ActivityFormularioGastoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        respetarBarrasDelSistema()

        // 2. El enum alimenta el Spinner.
        binding.spCategoria.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, Categoria.entries
        )

        // Configurar las monedas disponibles
        val monedas = listOf("GTQ", "USD")

        binding.spMoneda.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            monedas
        )

        // Mostrar el tipo de cambio solamente para dólares
        binding.spMoneda.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val moneda = monedas[position]

                    if (moneda == "USD") {
                        binding.contenedorTipoCambio.visibility = View.VISIBLE
                    } else {
                        binding.contenedorTipoCambio.visibility = View.GONE
                    }

                    actualizarEquivalente()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.contenedorTipoCambio.visibility = View.GONE
                }
            }


        // Actualizar la conversión al escribir el monto o tipo de cambio
        val observador = object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                actualizarEquivalente()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        binding.etMonto.addTextChangedListener(observador)
        binding.etTipoCambio.addTextChangedListener(observador)


        // 3. ¿Nos enviaron un id? 0 = no vino nada -> modo CREAR.
        val id = intent.getIntExtra(EXTRA_ID_GASTO, 0)
        if (id != 0) {
            lifecycleScope.launch { cargarGasto(id) }
        }

        binding.btnGuardar.setOnClickListener { guardar() }

        binding.btnEliminar.setOnClickListener {
            confirmarEliminacion()
        }
    }


    /** Modo EDITAR: busca el gasto y llena los campos con sus datos. */
    private suspend fun cargarGasto(id: Int) {
        val gasto = control.buscarPorId(id)
        if (gasto == null) {
            Toast.makeText(this, "No existe el gasto #$id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        gastoEditando = gasto
        binding.btnEliminar.visibility = View.VISIBLE

        binding.tvTitulo.text = "Editar gasto"
        binding.btnGuardar.text = "Guardar cambios"
        // Cuidado: un EditText se llena con setText(...), no con "text = ...".
        binding.etDescripcion.setText(gasto.descripcion)
        binding.etMonto.setText(gasto.monto.toString())
        // ordinal = posicion en el enum = posicion en el Spinner (lo llenamos con Categoria.entries).
        binding.spCategoria.setSelection(gasto.categoria.ordinal)

        val datosMoneda = Dependencias.gastos.obtenerMonedaYTipoCambio(id)

        if (datosMoneda != null) {
            val moneda = datosMoneda.first
            val tipoCambio = datosMoneda.second

            binding.spMoneda.setSelection(
                if (moneda == "USD") 1 else 0
            )

            binding.etTipoCambio.setText(
                if (moneda == "USD") tipoCambio.toString() else ""
            )
        }
    }


    private fun guardar() {

        val descripcion = binding.etDescripcion.text.toString().trim()
        val monto = binding.etMonto.text.toString().trim().toDoubleOrNull()
        val categoria = binding.spCategoria.selectedItem as Categoria

        val moneda = binding.spMoneda.selectedItem?.toString() ?: "GTQ"

        if (descripcion.isBlank()) {
            binding.etDescripcion.error = "Ingresa una descripción"
            return
        }

        if (monto == null || !monto.isFinite() || monto <= 0.0) {
            binding.etMonto.error = "Ingresa un monto válido"
            return
        }

        val tipoCambio = if (moneda == "USD") {
            binding.etTipoCambio.text.toString().trim().toDoubleOrNull()
        } else {
            1.0
        }

        if (tipoCambio == null ||
            !tipoCambio.isFinite() ||
            tipoCambio <= 0.0
        ) {
            binding.etTipoCambio.error = "Ingresa un tipo de cambio válido"
            return
        }

        if (!(monto * tipoCambio).isFinite()) {
            binding.etMonto.error = "El equivalente es demasiado grande"
            return
        }

        val original = gastoEditando

        val gasto = if (original == null) {
            Gasto(
                descripcion = descripcion,
                monto = monto,
                categoria = categoria,
                fecha = fechaDeHoy()
            )
        } else {
            original.copy(
                descripcion = descripcion,
                monto = monto,
                categoria = categoria
            )
        }

        binding.btnGuardar.isEnabled = false

        lifecycleScope.launch {
            try {
                if (original == null) {

                    Dependencias.gastos.agregarConMoneda(
                        gasto = gasto,
                        moneda = moneda,
                        tipoCambio = tipoCambio
                    )

                } else {

                    val actualizado = Dependencias.gastos.actualizarConMoneda(
                        gasto = gasto,
                        moneda = moneda,
                        tipoCambio = tipoCambio
                    )

                    if (!actualizado) {
                        Toast.makeText(
                            this@FormularioGastoActivity,
                            "No se encontró el gasto",
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.btnGuardar.isEnabled = true
                        return@launch
                    }
                }

                Toast.makeText(
                    this@FormularioGastoActivity,
                    "Gasto guardado correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            } catch (e: Exception) {

                binding.btnGuardar.isEnabled = true

                Toast.makeText(
                    this@FormularioGastoActivity,
                    "No se pudo guardar el gasto",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private fun confirmarEliminacion() {
        val gasto = gastoEditando ?: return

        AlertDialog.Builder(this)
            .setTitle("Eliminar gasto")
            .setMessage(
                "¿Estás seguro de que deseas eliminar este gasto?"
            )
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Eliminar") { _, _ ->
                lifecycleScope.launch {
                    val eliminado = control.eliminar(gasto.id)

                    if (eliminado) {
                        Toast.makeText(
                            this@FormularioGastoActivity,
                            "Gasto eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@FormularioGastoActivity,
                            "No se pudo eliminar el gasto",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            .show()
    }

    private fun actualizarEquivalente() {
        val moneda = binding.spMoneda.selectedItem?.toString() ?: "GTQ"

        if (moneda != "USD") {
            binding.tvEquivalente.text = "Equivalente: Q 0.00"
            return
        }

        val monto = binding.etMonto.text.toString().toDoubleOrNull()
        val tipoCambio = binding.etTipoCambio.text.toString().toDoubleOrNull()

        if (monto == null || tipoCambio == null ||
            !monto.isFinite() || !tipoCambio.isFinite() ||
            monto <= 0.0 || tipoCambio <= 0.0
        ) {
            binding.tvEquivalente.text = "Ingresa un monto y tipo de cambio válidos"
            return
        }

        val equivalente = monto * tipoCambio

        binding.tvEquivalente.text =
            if (equivalente.isFinite()) {
                String.format(
                    Locale.US,
                    "Equivalente: Q %.2f",
                    equivalente
                )
            } else {
                "El equivalente es demasiado grande"
            }
    }


    // Cuidado: java.time.LocalDate necesita API 26; con minSdk 24 usamos SimpleDateFormat.
    private fun fechaDeHoy(): String = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

    /**
     * Receta: con targetSdk 35+ la app se dibuja DEBAJO de la barra de estado.
     * Esto agrega arriba y abajo el espacio que ocupan las barras del sistema.
     */
    private fun respetarBarrasDelSistema() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { vista, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            vista.updatePadding(top = barras.top, bottom = barras.bottom)
            insets
        }
    }

    // companion object = "static" de Java. La lista usa esta constante para enviar el id.
    companion object {
        const val EXTRA_ID_GASTO = "id_gasto"
    }
}
