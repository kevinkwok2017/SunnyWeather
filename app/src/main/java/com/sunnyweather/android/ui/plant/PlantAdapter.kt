package com.sunnyweather.android.ui.plant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.databinding.PlantItemBinding
import com.sunnyweather.android.logic.model.Plant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PlantAdapter(
    private val onPlantClick: (Plant) -> Unit,
    private val onPlantLongClick: (Plant) -> Unit
) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {
    private val plants = ArrayList<Plant>()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    inner class ViewHolder(val binding: PlantItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PlantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plants[position]
        holder.binding.plantNameText.text = plant.name
        holder.binding.lastWaterTimeText.text = "上次浇水：${dateFormat.format(Date(plant.lastWaterTime))}"
        holder.itemView.setOnClickListener { onPlantClick(plant) }
        holder.itemView.setOnLongClickListener {
            onPlantLongClick(plant)
            true
        }
    }

    override fun getItemCount() = plants.size

    fun submitList(newPlants: List<Plant>) {
        plants.clear()
        plants.addAll(newPlants)
        notifyDataSetChanged()
    }
}
