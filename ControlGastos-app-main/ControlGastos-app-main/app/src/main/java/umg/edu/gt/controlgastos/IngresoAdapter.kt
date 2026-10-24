package umg.edu.gt.controlgastos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umg.edu.gt.controlgastos.datos.IngresoEntity
import umg.edu.gt.controlgastos.databinding.ItemIngresoBinding
import java.util.Locale

class IngresoAdapter(
    private val alSeleccionar: (IngresoEntity) -> Unit
) : RecyclerView.Adapter<IngresoAdapter.IngresoViewHolder>() {

    private var ingresos: List<IngresoEntity> = emptyList()

    fun mostrar(nuevosIngresos: List<IngresoEntity>) {
        ingresos = nuevosIngresos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngresoViewHolder {
        val binding = ItemIngresoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngresoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: IngresoViewHolder,
        position: Int
    ) {
        holder.mostrar(ingresos[position])
    }

    override fun getItemCount(): Int = ingresos.size

    inner class IngresoViewHolder(
        private val binding: ItemIngresoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun mostrar(ingreso: IngresoEntity) {

            binding.txtDescripcionIngreso.text = ingreso.descripcion

            if (ingreso.moneda == "USD") {

                val equivalente = ingreso.monto * ingreso.tipoCambio

                binding.txtMontoIngreso.text = String.format(
                    Locale.US,
                    "US$ %.2f",
                    ingreso.monto
                )

                binding.txtFechaIngreso.text = String.format(
                    Locale.US,
                    "%s · Equivalente: Q %.2f",
                    ingreso.fecha,
                    equivalente
                )

            } else {

                binding.txtMontoIngreso.text = String.format(
                    Locale.US,
                    "Q %.2f",
                    ingreso.monto
                )

                binding.txtFechaIngreso.text = ingreso.fecha
            }

            binding.root.setOnClickListener {
                alSeleccionar(ingreso)
            }
        }
    }
}
