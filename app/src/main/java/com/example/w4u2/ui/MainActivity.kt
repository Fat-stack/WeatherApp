package com.example.w4u2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.w4u2.R
import com.example.w4u2.data.db.DatabaseHandler
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // variables db-related
        val context = this
        val db = DatabaseHandler(context)

        // Variables ui-components
        val inputCity = findViewById<TextInputEditText>(R.id.inputEditText)
        val btnCheckWeather = findViewById<Button>(R.id.btnCheckWeather)


        btnCheckWeather.setOnClickListener {
            if (inputCity.text.toString().isNotEmpty()) {
                val intent = Intent(this, WeatherActivity::class.java)
                intent.putExtra(
                    "MessageStringCity", inputCity.text.toString()
                        .replace("\n", "")
                        .replace(" ", "")
                        .replaceFirstChar { it.uppercase() }
                )
                startActivity(intent)

            } else {
                Toast.makeText(this, "Enter CityField Correctly and try again", Toast.LENGTH_SHORT)
                    .show()
            }

        }


        val arrayAdapter: ArrayAdapter<String>

        val favoritesList = mutableListOf<String>()

        val lvFavorites = findViewById<ListView>(R.id.lvFavorites)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, favoritesList)
        lvFavorites.adapter = arrayAdapter


        val data = db.readData()

        for (i in 0 until data.size) {
            favoritesList.add(data[i].nameOfCity)

        }


        lvFavorites.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->

                val selectedItemText = parent.getItemAtPosition(position)

                val intent = Intent(this, WeatherActivity::class.java)
                intent.putExtra("MessageStringCity", selectedItemText.toString())
                startActivity(intent)

            }


    }


}


