package com.example.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**

 * @Author : Sounean

 * @Time : On 2022-09-27 15:27

 * @Description : SunnyWeatherApplication

 * @Warn :

 */
class SunnyWeatherApplication : Application() {

    companion object{
        const val TOKEN = "HnQnLfkf2dsAZSbJ"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}