package com.example.sunnyweather.logic.network

//import android.telecom.Call
import retrofit2.http.Query
import javax.security.auth.callback.Callback
import kotlin.coroutines.suspendCoroutine
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**

 * @Author : Sounean

 * @Time : On 2022-09-27 16:28

 * @Description : SunnyWeatherNetWork 统一的网络数据源访问入口，对所有网络请求的API进行封装

 * @Warn :

 */
object SunnyWeatherNetWork {

    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : retrofit2.Callback<T> {    // 这里书中是直接Callback<T> 并不是 retrofit2.Callback<T>
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}