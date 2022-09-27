package com.example.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**

 * @Author : Sounean

 * @Time : On 2022-09-27 16:15

 * @Description : ServiceCreator    Retrofit构造器

 * @Warn :

 */
object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)  // 只用上一句也可以。不过下方的增加了泛型实话，会让语法使用更加简洁
}