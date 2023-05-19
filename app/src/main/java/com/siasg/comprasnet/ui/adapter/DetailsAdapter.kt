package com.siasg.comprasnet.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.ListDetailsBinding
import com.siasg.comprasnet.databinding.ListResultadosBinding
import com.siasg.comprasnet.domain.Contrato
import java.util.*

class DetailsAdapter(private val detailsList: List<Pair<String, String>>) :
    RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsAdapter.DetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_details, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val (label, value) = detailsList[position]
        holder.bind(label, value)
    }

    override fun getItemCount(): Int = detailsList.size

    inner class DetailsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding: ListDetailsBinding = ListDetailsBinding.bind(v)

        private val tvLabel: TextView = binding.tvLabelDetails
        private val tvValue: TextView = binding.tvValueDetails

        fun bind(label: String, value: String) {
            tvLabel.text = label
            tvValue.text = value

        }
    }
}