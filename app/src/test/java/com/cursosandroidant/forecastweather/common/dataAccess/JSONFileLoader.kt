package com.cursosandroidant.forecastweather.common.dataAccess

import com.cursosandroidant.forecastweather.entities.WeatherForecastEntity
import com.google.gson.Gson
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader

class JSONFileLoader {
    private var jsonStr: String? = null
    fun loadJsonString(file:String):String?{
        val loader= InputStreamReader(this.javaClass.getResourceAsStream(file))
        jsonStr=loader.readText()
/*
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val inStream = InputStreamReader(fis)

        jsonStr=inStream.readText()
*/
        loader.close()
        return jsonStr
    }
    fun loadWeatherForecastEntry(file:String):WeatherForecastEntity?{
        val loader= InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        jsonStr=loader.readText()
        loader.close()
        return Gson().fromJson(jsonStr,WeatherForecastEntity::class.java)
    }
}