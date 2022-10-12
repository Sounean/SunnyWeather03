package com.example.sunnyweather.ui.weather


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Location

/**

 * @Author : Sounean

 * @Time : On 2022-10-11 13:51

 * @Description : WeatherViewModel 天气的ViewModel层

 * @Warn :

 */
class WeatherViewModel : ViewModel(){

    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""    // 这三个var用于保证手机屏幕发生旋转时数据不丢失

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        location -> Repository.refreshWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng: String, lat: String){   // 刷新天气信息
        locationLiveData.value = Location(lng, lat)
    }

}