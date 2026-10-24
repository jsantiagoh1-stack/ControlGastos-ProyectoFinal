package umg.edu.gt.controlgastos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.umg.programacion2.gastos.modelo.Gasto
import edu.umg.programacion2.gastos.servicio.formatearQuetzales
import umg.edu.gt.controlgastos.databinding.ItemGastoBinding
import edu.umg.programacion2.gastos.modelo.Categoria

/**
 * El "puente" entre la lista de datos (List<Gasto>) y las filas que se ven en pantalla.
 *
 * alTocarGasto es una FUNCION que recibe la Activity. El Adapter no sabe que pasa al tocar
 * un gasto (abrir el formulario, borrar, ...): solo avisa. La Activity decide.
 */
class GastoAdapter(
    private val alTocarGasto: (Gasto) -> Unit
) : RecyclerView.Adapter<GastoAdapter.GastoViewHolder>() {

    // Los datos que se muestran. Empieza vacia; la Activity la llena con mostrar(...).
    private var gastos: List<Gasto> = emptyList()
    private var monedasPorGasto: Map<Int, Pair<String, Double>> = emptyMap()

    /** Guarda las vistas de UNA fila para poder reutilizarla (reciclarla). */
    class GastoViewHolder(val binding: ItemGastoBinding) : RecyclerView.ViewHolder(binding.root)

    // 1. ¿Cuantas filas hay?
    override fun getItemCount(): Int = gastos.size

    // 2. Crear una fila NUEVA. RecyclerView lo llama solo unas pocas veces.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        // Cuidado: el ultimo parametro va en false; RecyclerView agrega la fila por su cuenta.
        val binding = ItemGastoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GastoViewHolder(binding)
    }

    // 3. Poner los datos del gasto "position" en una fila (nueva o reciclada).
    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        val gasto = gastos[position]
        holder.binding.tvDescripcion.text = gasto.descripcion
        val icono = when (gasto.categoria) {
            Categoria.ALIMENTACION -> R.drawable.ic_categoria_alimentacion
            Categoria.TRANSPORTE -> R.drawable.ic_categoria_transporte
            Categoria.ESTUDIOS -> R.drawable.ic_categoria_estudios
            Categoria.ENTRETENIMIENTO -> R.drawable.ic_categoria_entretenimiento
            Categoria.SALUD -> R.drawable.ic_categoria_salud
            Categoria.OTROS -> R.drawable.ic_categoria_otros
        }

        holder.binding.ivIconoGasto.setImageResource(icono)


        val datosMoneda = monedasPorGasto[gasto.id]
        val moneda = datosMoneda?.first ?: "GTQ"
        val tipoCambio = datosMoneda?.second ?: 1.0

        if (moneda == "USD") {
            holder.binding.tvMonto.text =
                String.format(java.util.Locale.US, "US$ %.2f", gasto.monto)

            holder.binding.tvDetalle.text =
                String.format(
                    java.util.Locale.US,
                    "%s · %s · Q %.2f",
                    gasto.categoria,
                    gasto.fecha,
                    gasto.monto * tipoCambio
                )
        } else {
            holder.binding.tvMonto.text = formatearQuetzales(gasto.monto)
            holder.binding.tvDetalle.text =
                "${gasto.categoria} · ${gasto.fecha}"
        }
        holder.binding.root.setOnClickListener {
            alTocarGasto(gasto) }
    }

    /** Reemplaza los datos y le pide a RecyclerView que redibuje la lista. */
    @SuppressLint("NotifyDataSetChanged")
    fun mostrar(
        nuevos: List<Gasto>,
        monedas: Map<Int, Pair<String, Double>> = emptyMap()
    ) {
        gastos = nuevos
        monedasPorGasto = monedas
        notifyDataSetChanged()
    }
}
