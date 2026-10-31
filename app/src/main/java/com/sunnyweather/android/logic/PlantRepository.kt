package com.sunnyweather.android.logic

import com.sunnyweather.android.logic.database.PlantDatabase
import com.sunnyweather.android.logic.model.Plant

object PlantRepository {
    private val plantDao = PlantDatabase.getDatabase().plantDao()

    fun getAllPlants() = plantDao.getAllPlants()

    suspend fun addPlant(name: String) {
        plantDao.insertPlant(Plant(name = name, lastWaterTime = System.currentTimeMillis()))
    }

    suspend fun deletePlant(plant: Plant) {
        plantDao.deletePlant(plant)
    }

    suspend fun waterPlant(plant: Plant) {
        plantDao.updatePlant(plant.copy(lastWaterTime = System.currentTimeMillis()))
    }
}
