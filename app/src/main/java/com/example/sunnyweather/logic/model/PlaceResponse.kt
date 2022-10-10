package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**

 * @Author : Sounean

 * @Time : On 2022-09-27 15:31

 * @Description : PlaceResponse

 * @Warn : 数据模型    将class关键字换成object关键字就直接帮生成单例类
 * 这个kt文件相当于以前的一个文件夹，里面放着三个实体类

 */

data class PlaceResponse(
    val status: String,
    val places: List<Place>
)    // data class的话会自动生成toString等方法

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)   // @SerializedName关键字将后台返回的formatted_address映射成address供客户端使用

data class Location(val lng: String, val lat: String)
