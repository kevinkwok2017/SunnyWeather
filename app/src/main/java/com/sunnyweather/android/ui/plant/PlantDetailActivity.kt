package com.sunnyweather.android.ui.plant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.android.databinding.ActivityPlantDetailBinding
import com.sunnyweather.android.logic.model.Plant

class PlantDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantDetailBinding
    private val viewModel by lazy { ViewModelProvider(this).get(PlantViewModel::class.java) }
    private lateinit var plant: Plant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plant = Plant(
            id = intent.getLongExtra(EXTRA_ID, 0),
            name = intent.getStringExtra(EXTRA_NAME).orEmpty(),
            lastWaterTime = intent.getLongExtra(EXTRA_LAST_WATER_TIME, 0)
        )
        binding.toolbar.title = plant.name
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.waterButton.setOnClickListener {
            viewModel.waterPlant(plant)
            plant = plant.copy(lastWaterTime = System.currentTimeMillis())
            Toast.makeText(this, "已记录今天浇水", Toast.LENGTH_SHORT).show()
        }
        binding.adviceButton.setOnClickListener {
            Toast.makeText(this, "养护意见功能暂未开放", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val EXTRA_ID = "plant_id"
        private const val EXTRA_NAME = "plant_name"
        private const val EXTRA_LAST_WATER_TIME = "last_water_time"

        fun actionStart(context: Context, plant: Plant) {
            context.startActivity(Intent(context, PlantDetailActivity::class.java).apply {
                putExtra(EXTRA_ID, plant.id)
                putExtra(EXTRA_NAME, plant.name)
                putExtra(EXTRA_LAST_WATER_TIME, plant.lastWaterTime)
            })
        }
    }
}
