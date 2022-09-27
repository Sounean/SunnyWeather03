package com.example.sunnyweather.logic.network

import retrofit2.http.Query

/**

 * @Author : Sounean

 * @Time : On 2022-09-27 16:28

 * @Description : SunnyWeatherNetWork 统一的网络数据源访问入口，对所有网络请求的API进行封装

 * @Warn :

 */
object SunnyWeatherNetWork {

    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private suspend fun
}