package com.sunnyweather.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.ui.weather.WeatherActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Repository.isPlaceSaved()) {
            val place = Repository.getSavedPlace()
            WeatherActivity.actionStart(this, place.name, place.location.lng, place.location.lat)
            finish()
        } else {
            setContentView(R.layout.activity_main)
        }
    }
}
