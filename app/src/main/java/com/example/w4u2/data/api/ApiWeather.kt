package com.example.w4u2.data.api

import com.example.w4u2.data.model.Wxr
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiWeather {

    @GET("/weather/{city}")
    fun getWeather(@Path("city") city : String) : Call<Wxr>
}
