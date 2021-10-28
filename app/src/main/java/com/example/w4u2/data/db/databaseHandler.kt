package com.example.w4u2.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.w4u2.data.model.City


const val DATABASE_NAME = "City2DB"
const val TABLE_NAME = "Cities"
const val COL_ID = "id"
const val COL_NAME_OF_CITY = "nameOfCity"

class DatabaseHandler(private var context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAME_OF_CITY VARCHAR(256))"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(city: City) {
        if (checkIfNOTinDatabase(city)) {

            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(COL_NAME_OF_CITY, city.nameOfCity)

            val result = db.insert(TABLE_NAME, null, cv)
            if (result == (0).toLong()) {
                Toast.makeText(context, "Fail to store city", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "${city.nameOfCity} Stored as favorite", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(context, "${city.nameOfCity} already stored", Toast.LENGTH_SHORT).show()
        }

    }

    fun readData(): MutableList<City> {

        val list: MutableList<City> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val city = City()
                city.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                city.nameOfCity = result.getString(result.getColumnIndex(COL_NAME_OF_CITY))
                list.add(city)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun deleteItem(cit: String): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, "$COL_NAME_OF_CITY=?", arrayOf(cit))
        db.close()

        Toast.makeText(context, "${cit} removed from favorites", Toast.LENGTH_SHORT)
            .show()

        return success
    }

    // If city with same name NOT already exist return true
    private fun checkIfNOTinDatabase(city: City): Boolean {
        for (i in 0 until readData().size) {
            if (readData()[i].nameOfCity == city.nameOfCity) {
                return false
            }
        }
        return true
    }


}