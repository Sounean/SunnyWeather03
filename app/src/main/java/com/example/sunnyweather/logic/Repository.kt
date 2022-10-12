package com.example.sunnyweather.logic

import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.dao.PlaceDao
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.model.PlaceResponse
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.http.Query
import kotlin.coroutines.CoroutineContext

/**

 * @Author : Sounean

 * @Time : On 2022-10-10 14:16

 * @Description : Repository ，仓库
层的主要工作就是判断调用方请求的数据应该是从本地数据源中获取还是从网络数据源中获
取，并将获得的数据返回给调用方。因此，仓库层有点像是一个数据获取与缓存的中间层，在
本地没有缓存数据的情况下就去网络层获取，如果本地已经有缓存了，就直接将缓存数据返
回

 * @Warn : 作为仓库层的统一封装入口

 */
object Repository {

    /*
    * 地点搜索
    * */
//    fun searchPlace(query: String) = liveData(Dispatchers.IO) {// 网络请求是耗时操作，需要切换一IC
//        val result = try {
    fun searchPlace(query: String) = fire(Dispatchers.IO) {// 网络请求是耗时操作，需要切换一IC
        val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
            if (placeResponse.status == "ok") {// 如果服务器响应状态是ok，就用Kotlin内置的Result.success()来包装获取的城市数据列表
                val places = placeResponse.places
                Result.success(places)
            } else {//如果服务器响应状态不是ok，包装一个异常信息
                Result.failure(RuntimeException("response status is $ {placeResponse.status}"))
            }
//        } catch (e: Exception) {
//            Result.failure<List<Place>>(e)
//        }
//        emit(result)// 相当于调用LiveData的setValue()方法来通知数据变化
    }

    /*
    * 天气搜索
    * 不同点：此处与地点搜索不同的是要实现实时天气和未来天气的并发执行，且在同时得到它们响应结果后再进一步执行程序。用到了async和await()方法
    * 注意：又因为async函数需在协程作用域内才能调用，所以又用了coroutineScope函数创建了一个协程作用域
    * */
//    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
//        val result = try {
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
    coroutineScope {
                val deferredRealtime = async {
                    SunnyWeatherNetWork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetWork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather = Weather(
                        realtimeResponse.result.realtime,
                        dailyResponse.result.daily
                    )
                    Result.success(weather)
                } else {
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}" +
                                    "daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
//        } catch (e: Exception) {
//            Result.failure<Weather>(e)
//        }
//        emit(result)
    }

    /*
    * fire()函数是一个按照liveData()函数的参数
    接收标准定义的一个高阶函数。在fire()函数的内部会先调用一下liveData()函数，然后在
    liveData()函数的代码块中统一进行了try catch处理，并在try语句中调用传入的Lambda
    表达式中的代码，最终获取Lambda表达式的执行结果并调用emit()方法发射出去。
    *注意：在liveData()函数的代码块中，我们是拥有挂起函数上下文的，可
    是当回调到Lambda表达式中，代码就没有挂起函数上下文了，但实际上Lambda表达式中的
    代码一定也是在挂起函数中运行的。为了解决这个问题，我们需要在函数类型前声明一个
    suspend关键字，以表示所有传入的Lambda表达式中的代码也是拥有挂起函数上下文的
    * 上方注释掉的部分是用fire省略掉了
    * */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }


    /*
    * 保存地址到本地SP中去  (其实仅进行了一次封装)
    * */
    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

}