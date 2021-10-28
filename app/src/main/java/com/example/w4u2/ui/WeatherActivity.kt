package com.example.w4u2.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.w4u2.R
import com.example.w4u2.data.api.ApiWeather
import com.example.w4u2.data.db.DatabaseHandler
import com.example.w4u2.data.model.City
import com.example.w4u2.data.model.Wxr
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate



class WeatherActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        Log.d("robin", "WA 26")

        //Variables db-related
        val context = this
        val db = DatabaseHandler(context)

        // Variables ui-components
        val tvCity = findViewById<TextView>(R.id.tvCity)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val tvTemperature = findViewById<TextView>(R.id.tvTemperature)
        val tvWind = findViewById<TextView>(R.id.tvWind)
        val tvForecast = findViewById<TextView>(R.id.tvForecast)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnAddToFavorites = findViewById<ImageButton>(R.id.btnAddToFavorites)
        val btnDeleteItem = findViewById<ImageButton>(R.id.btnDeleteItem)


        // variable keeping intent Extras from MainActivity (city)
        val city = intent.getStringExtra("MessageStringCity").toString()

        // variable keeping built retrofit case with GsonConverter
        val retrofit = Retrofit.Builder()
            .baseUrl("https://goweather.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // variable keeping retrofit created api-interface, to access @GET-method
        val service = retrofit.create(ApiWeather::class.java)

        //
        // Endpoint access via GetWeather() in API, with String city from intent-message
        val call = service.getWeather(city)

        // Response from request and how to use requested data.
        call.enqueue(object : Callback<Wxr> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<Wxr>, response: Response<Wxr>) {
                if (response.isSuccessful) {
                    val wxr = response.body()

                    tvCity.text = city
                    tvDescription.text = "${wxr?.description}"
                    tvTemperature.text = "${wxr?.temperature}"
                    tvWind.text = "Wind: ${wxr?.wind}"

                    tvForecast.text = ""
                    val forecastData = wxr?.forecast

                    for (i in 0 until forecastData?.size!!) {

                        tvForecast.append(
                            forecastData[i].toString()
                                .replace("Day: 1", "TOMORROW:")
                                .replace("Day: 2", LocalDate.now().dayOfWeek.plus(2).name)
                                .replace("Day: 3", LocalDate.now().dayOfWeek.plus(3).name)

                        )

                    }
                }

            }

            override fun onFailure(call: Call<Wxr>, t: Throwable) {

            }

        })




        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnAddToFavorites.setOnClickListener {
            db.insertData(City(city))

        }

        btnDeleteItem.setOnClickListener {
            db.deleteItem(city)
        }


    }

}