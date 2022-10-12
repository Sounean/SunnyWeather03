package com.example.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.Place
import com.google.gson.Gson

/**

 * @Author : Sounean

 * @Time : On 2022-10-12 19:35

 * @Description : PlaceDao

 * @Warn :

 */
object PlaceDao {

    private fun sharedPreference() = SunnyWeatherApplication.context
        .getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)

    fun savePlace(place:Place){
        sharedPreference().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreference().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreference().contains("place")

}