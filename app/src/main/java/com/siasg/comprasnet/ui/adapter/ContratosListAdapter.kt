package com.siasg.comprasnet.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.ListResultadosBinding
import com.siasg.comprasnet.domain.Contrato
import com.siasg.comprasnet.ui.fragment.home.ResultFragment
import com.siasg.comprasnet.ui.fragment.more.FavoritesFragment
import java.time.LocalDate
import java.util.*


class ContratosListAdapter(
    private val contratos: List<Contrato>,
    private var listenerFavorites: FavoritesFragment? = null,
    private var listenerResult: ResultFragment? = null
) :
    RecyclerView.Adapter<ContratosListAdapter.ContratosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContratosViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_resultados, parent, false)
        return ContratosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContratosViewHolder, position: Int) {
        val contrato = contratos[position]
        holder.bind(contrato)
        holder.itemView.setOnClickListener {
            if (listenerResult != null)
                listenerResult?.onContratoClick(contrato)
            if (listenerFavorites != null)
                listenerFavorites?.onContratoClick(contrato)
        }
    }

    override fun getItemCount() = contratos.size

    inner class ContratosViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding: ListResultadosBinding = ListResultadosBinding.bind(v)

        private val tvTitle: TextView = binding.cardTitle
        private val tvSub1: TextView = binding.cardSubtitle
        private val tvSub2: TextView = binding.cardSubtitle2
        private val colorCard: ImageView = binding.colorCard

        @SuppressLint("SetTextI18n")
        fun bind(contrato: Contrato) {
            val color = setDateColor(contrato.vigencia_fim)
            tvTitle.text = "ID: ${contrato.id} - Categoria: ${contrato.categoria}"
            tvSub1.text = "UASG: ${contrato.orgao_codigo} - ${contrato.unidade_nome_resumido}"
            tvSub2.text = contrato.objeto.lowercase(Locale.getDefault())
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            colorCard.setBackgroundResource(color)
        }
    }

    fun setDateColor(date: String): Int {
        val date_contrato = LocalDate.parse(date).toEpochDay()
        val date_hoje = LocalDate.now().toEpochDay()
        val days = (date_contrato - date_hoje)
        val daysElapsed = Math.abs(days)

        return when {
            daysElapsed < 30 -> R.color.red_vencem_30dias
            daysElapsed in 30..59 -> R.color.orange_vencem_30_60
            daysElapsed in 60..89 -> R.color.yellow_vencem_60_90
            daysElapsed in 90..179 -> R.color.blue_vencem_90_180
            else -> R.color.blue_vencem_180
        }

    }
}