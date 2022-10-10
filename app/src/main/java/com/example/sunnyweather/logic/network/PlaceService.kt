package com.example.sunnyweather.logic.network

//import android.telecom.Call
import com.example.sunnyweather.logic.model.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

/**

 * @Author : Sounean

 * @Time : On 2022-09-27 15:41

 * @Description : PlaceService

 * @Warn :

 */
interface PlaceService {

    // 示例：https://api.caiyunapp.com/v2/place?query=北京&token={HnQnLfkf2dsAZSbJ}&lang=zh_CN 仅query关键字是动态指定的
    @GET("v2/place?token={SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>    //Call<PlaceResponse>是返回值类型,Retrofit自动解析服务器返回的JSON数据成PlaceResponse对象.若没返回东西就Call<ResponseBody>
}