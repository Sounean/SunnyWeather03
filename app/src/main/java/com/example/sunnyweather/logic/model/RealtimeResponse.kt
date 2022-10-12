package com.example.sunnyweather.logic.model

import androidx.core.location.LocationRequestCompat.Quality
import com.google.gson.annotations.SerializedName

/**

 * @Author : Sounean

 * @Time : On 2022-10-11 10:22

 * @Description : RealtimeResponse 获取实时天气信息的逻辑层

 * @Warn :

 */

/*{
    "status": "ok",
    "result": {
        "realtime": {
            "temperature": 23.16,
            "skycon": "WIND",
            "air_quality": {
                "aqi": { "chn": 17.0 }
             }
        }
    }
}*/
data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(val skycon: String,val temperature: Float,
                @SerializedName("air_quality") val airQuality: AirQuality)

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)

}