package com.sunnyweather.android.ui.place

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunnyweather.android.R

class PlaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, PlaceActivity::class.java))
        }
    }
}
