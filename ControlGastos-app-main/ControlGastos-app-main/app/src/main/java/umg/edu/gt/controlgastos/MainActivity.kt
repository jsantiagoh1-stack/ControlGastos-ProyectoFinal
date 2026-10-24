package umg.edu.gt.controlgastos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import umg.edu.gt.controlgastos.databinding.ActivityMainBinding

/** Pantalla principal: el total, el boton "+" y la lista de gastos. */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GastoAdapter

    private lateinit var ingresoAdapter: IngresoAdapter

    // IMPORTANTE: ya NO se crea aqui; se usa el compartido (ver Dependencias.kt).
    private val control = Dependencias.control

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Inflar el layout (igual que en la Clase 12).
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        respetarBarrasDelSistema()

        // 2. Crear el Adapter. El bloque { } se ejecuta cuando el usuario toca un gasto.
        adapter = GastoAdapter { gasto -> abrirFormulario(gasto.id) }

        // 3. Conectar la lista. Cuidado: sin LayoutManager la lista queda en blanco y sin error.
        binding.rvGastos.layoutManager = LinearLayoutManager(this)
        binding.rvGastos.adapter = adapter

        ingresoAdapter = IngresoAdapter { ingreso ->
            val intent = Intent(this, FormularioIngresoActivity::class.java)
            intent.putExtra("id_ingreso", ingreso.id)
            startActivity(intent)
        }

        binding.rvIngresos.layoutManager = LinearLayoutManager(this)
        binding.rvIngresos.adapter = ingresoAdapter

        // 4. El boton "+" abre el formulario vacio (sin id).
        binding.btnNuevo.setOnClickListener { abrirFormulario(null) }

        // 5. El botón "Nuevo ingreso" abre el formulario de ingresos.
        binding.btnNuevoIngreso.setOnClickListener {
            val intent = Intent(
                this,
                FormularioIngresoActivity::class.java
            )
            startActivity(intent)
        }
    }

    // IMPORTANTE: onResume se ejecuta al abrir la pantalla Y cada vez que regresamos del
    // formulario. onCreate NO se vuelve a ejecutar al regresar.
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch { cargarGastos() }
    }


    private suspend fun cargarGastos() {


        val gastos = control.listar()
        val monedasPorGasto = Dependencias.gastos.obtenerMonedasPorGasto()
        adapter.mostrar(gastos, monedasPorGasto)

        val totalGastos = Dependencias.gastos.totalGastosEnQuetzales()

        binding.tvResumen.text =
            "${gastos.size} gastos · Total: Q %.2f".format(totalGastos)

        val totalIngresos = Dependencias.ingresos.totalIngresos()

        val listaIngresos = Dependencias.ingresos.listar()
        ingresoAdapter.mostrar(listaIngresos)

        val saldo = totalIngresos - totalGastos

        binding.tvTotalIngresos.text =
            "Ingresos: Q %.2f".format(totalIngresos)

        binding.tvTotalGastos.text =
            "Gastos: Q %.2f".format(totalGastos)

        binding.tvSaldoDisponible.text =
            "Saldo disponible: Q %.2f".format(saldo)
    }


    /** id == null -> crear un gasto nuevo. id con valor -> editar ese gasto. */
    private fun abrirFormulario(id: Int?) {
        val intent = Intent(this, FormularioGastoActivity::class.java)
        if (id != null) {
            intent.putExtra(FormularioGastoActivity.EXTRA_ID_GASTO, id)
        }
        startActivity(intent)
    }

    /** Receta: espacio para la barra de estado y la de navegacion (targetSdk 35+). */
    private fun respetarBarrasDelSistema() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { vista, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            vista.updatePadding(top = barras.top, bottom = barras.bottom)
            insets
        }
    }
}
