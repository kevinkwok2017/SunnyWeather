package com.sunnyweather.android.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import com.sunnyweather.android.logic.model.Plant

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)

    @Query("SELECT * FROM plants ORDER BY id DESC")
    fun getAllPlants(): LiveData<List<Plant>>
}
