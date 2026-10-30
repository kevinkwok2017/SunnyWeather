package com.sunnyweather.android.logic.dao

import Place
import android.content.Context
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication

/** 管理用户最后一次选择的城市。 */
object PlaceDao {
    private const val PREFS_NAME = "sunny_weather"
    private const val PLACE_KEY = "place"

    fun savePlace(place: Place) {
        sharedPreferences().edit()
            .putString(PLACE_KEY, Gson().toJson(place))
            .apply()
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString(PLACE_KEY, "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved(): Boolean = sharedPreferences().contains(PLACE_KEY)

    private fun sharedPreferences() = SunnyWeatherApplication.context
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
}
