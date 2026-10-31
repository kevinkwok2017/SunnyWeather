package com.sunnyweather.android.ui.plant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyweather.android.logic.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantViewModel : ViewModel() {
    val plantList = PlantRepository.getAllPlants()

    fun addPlant(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            PlantRepository.addPlant(name)
        }
    }

    fun deletePlant(plant: com.sunnyweather.android.logic.model.Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            PlantRepository.deletePlant(plant)
        }
    }

    fun waterPlant(plant: com.sunnyweather.android.logic.model.Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            PlantRepository.waterPlant(plant)
        }
    }
}
