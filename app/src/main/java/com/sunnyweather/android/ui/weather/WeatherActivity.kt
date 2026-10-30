package com.sunnyweather.android.ui.weather

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.android.databinding.ActivityWeatherBinding
import com.sunnyweather.android.logic.model.Weather
import kotlin.math.roundToInt

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val placeName = intent.getStringExtra(EXTRA_PLACE_NAME) ?: "天气"
        val lng = intent.getStringExtra(EXTRA_LNG) ?: return
        val lat = intent.getStringExtra(EXTRA_LAT) ?: return
        window.statusBarColor = getColor(com.sunnyweather.android.R.color.colorPrimary)
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshWeather(currentLng, currentLat)
        }

        viewModel.weatherLiveData.observe(this) { result ->
            binding.swipeRefresh.isRefreshing = false
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                binding.weatherLayout.visibility = View.GONE
                Toast.makeText(this, "天气信息获取失败", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
        refreshWeather(placeName, lng, lat)
    }

    private var currentLng = ""
    private var currentLat = ""

    fun refreshWeather(placeName: String, lng: String, lat: String) {
        currentLng = lng
        currentLat = lat
        binding.toolbar.title = placeName
        viewModel.refreshWeather(lng, lat)
    }

    fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun showWeatherInfo(weather: Weather) {
        binding.weatherLayout.visibility = View.VISIBLE
        val realtime = weather.realtime
        binding.currentTempText.text = "${realtime.temperature.roundToInt()}℃"
        binding.currentSkyText.text = getSkyconText(realtime.skycon)
        binding.currentAqiText.text = "空气质量 ${realtime.airQuality.aqi.chn.roundToInt()}"

        val daily = weather.daily
        val todaySkycon = daily.skycon.firstOrNull()?.value.orEmpty()
        val todayTemp = daily.temperature.firstOrNull()
        binding.todaySkyText.text = getSkyconText(todaySkycon)
        binding.todayTempText.text = if (todayTemp == null) "暂无数据" else {
            "${todayTemp.min.roundToInt()}℃ ~ ${todayTemp.max.roundToInt()}℃"
        }
        binding.coldRiskText.text = "感冒：${daily.lifeIndex.coldRisk.firstOrNull()?.desc ?: "暂无数据"}"
        binding.carWashingText.text = "洗车：${daily.lifeIndex.carWashing.firstOrNull()?.desc ?: "暂无数据"}"
        binding.ultravioletText.text = "紫外线：${daily.lifeIndex.ultraviolet.firstOrNull()?.desc ?: "暂无数据"}"
        binding.dressingText.text = "穿衣：${daily.lifeIndex.dressing.firstOrNull()?.desc ?: "暂无数据"}"
    }

    private fun getSkyconText(skycon: String): String = when (skycon) {
        "CLEAR_DAY" -> "晴"
        "CLEAR_NIGHT" -> "晴"
        "PARTLY_CLOUDY_DAY" -> "多云"
        "PARTLY_CLOUDY_NIGHT" -> "多云"
        "CLOUDY" -> "阴"
        "LIGHT_HAZE", "MODERATE_HAZE", "HEAVY_HAZE" -> "雾霾"
        "LIGHT_RAIN", "MODERATE_RAIN", "HEAVY_RAIN", "STORM_RAIN" -> "雨"
        "LIGHT_SNOW", "MODERATE_SNOW", "HEAVY_SNOW", "STORM_SNOW" -> "雪"
        "WIND" -> "大风"
        else -> "未知"
    }

    companion object {
        private const val EXTRA_PLACE_NAME = "place_name"
        private const val EXTRA_LNG = "lng"
        private const val EXTRA_LAT = "lat"

        fun actionStart(context: Context, placeName: String, lng: String, lat: String) {
            context.startActivity(Intent(context, WeatherActivity::class.java).apply {
                putExtra(EXTRA_PLACE_NAME, placeName)
                putExtra(EXTRA_LNG, lng)
                putExtra(EXTRA_LAT, lat)
            })
        }
    }
}
