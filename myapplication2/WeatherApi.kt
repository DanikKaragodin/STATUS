package com.example.myapplication2
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson

//class WeatherApi {
//    private val client = OkHttpClient()
//    private val gson = Gson()
//
//    fun getCurrentWeather(q: String, key: String): WeatherData? {
//        val url = "http://api.weatherapi.com/v1/current.json?key=$key&q=$q&aqi=no"
//        val request = Request.Builder().url(url).build()
//        val response = client.newCall(request).execute()
//
//        if (!response.isSuccessful) {
//            return null
//        }
//
//        val json = response.body?.string()
//        val weatherData = gson.fromJson(json, WeatherData::class.java)
//        return weatherData
//    }
//}

//class WeatherApi {
//    private val client = OkHttpClient()
//    private val gson = Gson()
//
//    fun getCurrentWeather(q: String, key: String): WeatherData? {
//        val url = "http://api.weatherapi.com/v1/current.json?key=$key&q=$q&aqi=no"
//        val request = Request.Builder().url(url).build()
//        val response = client.newCall(request).execute()
//
//        if (!response.isSuccessful) {
//            return null
//        }
//
//        val body = response.body
//        val json = body!!.string()
//        val weatherData = gson.fromJson(json, WeatherData::class.java)
//        return weatherData
//    }
//}

class WeatherApi {
    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun getCurrentWeather(q: String, key: String): WeatherData? {
        val url = "http://api.weatherapi.com/v1/current.json?key=$key&q=$q&aqi=no"
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            return null
        }

        val body = response.body
        val json = body!!.string()
        val weatherData = gson.fromJson(json, WeatherData::class.java)
        return weatherData
    }
}

data class WeatherData(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Long,
    val localtime: String
)

data class Current(
    val temp_c: Double,
    val humidity: Int
)
