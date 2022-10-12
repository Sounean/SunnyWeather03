package com.example.sunnyweather.logic.model

/**

 * @Author : Sounean

 * @Time : On 2022-10-11 11:11

 * @Description : Weather用于将Realtime和Daily对象
封装起来

 * @Warn :

 */
data class Weather(val realtime: RealtimeResponse.Realtime,
                   val daily: DailyResponse.Daily)