package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("v2.6/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/weather")
    fun getWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String,
        @Query("dailysteps") dailySteps: Int = 3
    ): Call<WeatherResponse>
}
