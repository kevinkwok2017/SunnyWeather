package com.sunnyweather.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunnyweather.android.databinding.ActivityMainBinding
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.ui.place.PlaceActivity
import com.sunnyweather.android.ui.plant.PlantActivity
import com.sunnyweather.android.ui.weather.WeatherActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.plantManagementButton.setOnClickListener {
            startActivity(android.content.Intent(this, PlantActivity::class.java))
        }
        binding.weatherButton.setOnClickListener {
            openWeatherFeature()
        }
    }

    private fun openWeatherFeature() {
        if (Repository.isPlaceSaved()) {
            val place = Repository.getSavedPlace()
            WeatherActivity.actionStart(this, place.name, place.location.lng, place.location.lat)
        } else {
            PlaceActivity.actionStart(this)
        }
    }
}
