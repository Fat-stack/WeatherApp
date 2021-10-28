package com.example.w4u2.data.model

import com.google.gson.annotations.SerializedName

class Forecast {
    @SerializedName("day")
    val day : Int = 0

    @SerializedName("temperature")
    val temperature : String = ""

    @SerializedName("wind")
    val wind : String = ""


    override fun toString(): String {


        return "Day: $day \n" +
                "Temperature: $temperature \n" +
                "Wind: $wind \n \n"
    }
}