package com.rggn.appcondominio.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rggn.appcondominio.R
import com.rggn.appcondominio.data.CommonArea

class CommonAreaAdapter(
    private var areas: List<CommonArea> = emptyList(),
    private val onAreaClick: (Int) -> Unit
) : RecyclerView.Adapter<CommonAreaAdapter.AreaViewHolder>() {

    fun updateAreas(newAreas: List<CommonArea>) {
        areas = newAreas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_area_comum, parent, false)
        return AreaViewHolder(view, onAreaClick)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        val area = areas[position]
        holder.bind(area)
    }

    override fun getItemCount(): Int = areas.size

    class AreaViewHolder(itemView: View, private val onAreaClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.area_name)
        private val unitTextView: TextView = itemView.findViewById(R.id.area_details)

        fun bind(area: CommonArea) {
            nameTextView.text = area.name
            unitTextView.text = "Capacidade: ${area.capacity}"

            itemView.setOnClickListener {
                onAreaClick(area.id)
            }
        }
    }
}