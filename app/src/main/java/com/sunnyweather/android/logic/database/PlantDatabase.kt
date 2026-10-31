package com.sunnyweather.android.logic.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.dao.PlantDao
import com.sunnyweather.android.logic.model.Plant

@Database(entities = [Plant::class], version = 1, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    companion object {
        private var instance: PlantDatabase? = null

        fun getDatabase(): PlantDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    SunnyWeatherApplication.context,
                    PlantDatabase::class.java,
                    "plant_database"
                ).build().also { instance = it }
            }
        }
    }
}
