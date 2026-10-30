package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(val status: String, val result: Weather)

data class Weather(
    val realtime: RealtimeResponse,
    val daily: DailyResponse
)

data class RealtimeResponse(
    val temperature: Float,
    val skycon: String,
    @SerializedName("air_quality") val airQuality: AirQuality
)

data class AirQuality(val aqi: AQI)

data class AQI(val chn: Float)

data class DailyResponse(
    val skycon: List<Skycon>,
    val temperature: List<Temperature>,
    @SerializedName("life_index") val lifeIndex: LifeIndex
)

data class Skycon(val value: String)

data class Temperature(val max: Float, val min: Float)

data class LifeIndex(
    @SerializedName("coldRisk") val coldRisk: List<LifeDescription>,
    @SerializedName("carWashing") val carWashing: List<LifeDescription>,
    val ultraviolet: List<LifeDescription>,
    val dressing: List<LifeDescription>
)

data class LifeDescription(val desc: String)
