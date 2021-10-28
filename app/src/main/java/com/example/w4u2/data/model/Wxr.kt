package com.example.w4u2.data.model

import com.google.gson.annotations.SerializedName

class Wxr {

    @SerializedName("temperature")
    val temperature: String =""

    @SerializedName("wind")
    val wind: String =""

    @SerializedName("description")
    val description: String =""

    @SerializedName("forecast")
    val forecast: List<Forecast> = ArrayList()


}