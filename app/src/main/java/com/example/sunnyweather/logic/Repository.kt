package com.example.sunnyweather.logic

import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.model.PlaceResponse
import com.example.sunnyweather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Query

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

    fun searchPlace (query: String) = liveData(Dispatchers.IO) {// 网络请求是耗时操作，需要切换一IC
        val result = try {
            val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
            if (placeResponse.status == "ok"){// 如果服务器响应状态是ok，就用Kotlin内置的Result.success()来包装获取的城市数据列表
                val places = placeResponse.places
                Result.success(places)
            }else{//如果服务器响应状态不是ok，包装一个异常信息
                Result.failure(RuntimeException("response status is $ {placeResponse.status}"))
            }

        }catch (e :Exception){
            Result.failure<List<Place>>(e)
        }

        emit(result)// 相当于调用LiveData的setValue()方法来通知数据变化
    }
}