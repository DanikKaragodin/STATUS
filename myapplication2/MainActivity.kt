//package com.example.myapplication2
package com.example.myapplication2

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.FragmentActivity
import java.net.URL
import kotlinx.coroutines.*
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.status.main.app.R


class MainActivity : FragmentActivity() {
    private val weatherApi = WeatherApi()

    private var text2: String = ""

    // Блоки отвечающие за погоду
    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
//    override fun onPause() {
//        super.onPause()
//        lifecycleScope.cancel()
//    }
//    fun UpdateWeather(){
//        val temperature = findViewById<TextView> (R.id.textViewTempEdit)
//        val Humidity = findViewById<TextView> (R.id.textViewHumidityEdit)
//
//        if (temperature == null || Humidity == null) {
//            Log.e("Weather", "Views not found")
//            return
//        }
//
//        lifecycleScope.launch(Dispatchers.IO) {
//            while (true) {
//                try {
//                    val weatherData = weatherApi.getCurrentWeather("Brest", "f397fd74b18743b997c120315241606")
//                    if (weatherData != null) {
//                        val tempC = weatherData.current.temp_c
//                        val humidity = weatherData.current.humidity
//                        Log.d("Weather", "Completed to get weather data $tempC $humidity")
//                        withContext(Dispatchers.Main) {
//                            temperature.text = "Температура: $tempC °С"
//                            Humidity.text = "Влажность: $humidity %"
//                            Log.d("Layout", "${temperature.text} ${Humidity.text}")
//                        }
//                    } else {
//                        Log.d("Weather", "Failed to get weather data")
//                    }
//                } catch (e: Exception) {
//                    Log.e("Weather", "Error updating weather", e)
//                }
//                delay(15 * 60 * 1000) // wait for 15 minutes
//            }
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        UpdateWeather()
//    }
    // Конец блоков отвечающие за погоду

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Попытка в главное окно
        HomeAppRegister().registerHomeApp(this)
        // Функция отвечающая за погоду
        val temperature = findViewById<TextView> (R.id.textViewTempEdit)
//        val Humidity = findViewById<TextView> (R.id.textViewHumidityEdit)
        lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                try {
                    val weatherData = weatherApi.getCurrentWeather("Brest", "f397fd74b18743b997c120315241606")
                    if (weatherData != null) {
                        val tempC = weatherData.current.temp_c
//                        val humidity = weatherData.current.humidity
                        Log.d("Weather", "Completed to get weather data $tempC")
                        withContext(Dispatchers.Main) {
                            temperature.text = "$tempC °С"
//                            Humidity.text = "Влажность: $humidity %"
                            //Log.d("Layout", "${temperature.text} ${Humidity.text}")
                        }
                    } else {
                        Log.d("Weather", "Failed to get weather data")
                    }
                } catch (e: Exception) {
                    Log.e("Weather", "Error updating weather", e)
                }
                delay(15 * 60 * 1000) // wait for 15 minutes
            }}


        val marquee = findViewById<TextView> (R.id.marquee)
        marquee.isSelected = true


        val radioButton1 = findViewById<RadioButton>(R.id.radioButton)
        radioButton1.isChecked = true

        val videoView = findViewById<VideoView>(R.id.videoView2)

        //val videoSource = "file:///android_asset/video-status-01.mp4"
        //val videoSource = "https://www.dl.dropboxusercontent.com/s/jpjp3p5qjjwp9pp/video-status-01.mp4?dl=0"
        //val videoSource = "http://10.10.0.5/statusVideo/video-status-01.mp4"
        val videoSource = "android.resource://" + packageName + "/" + R.raw.videostatus3;


        videoView.setVideoURI(Uri.parse(videoSource))
        videoView.requestFocus(0)
        videoView.start()
        videoView.isEnabled = false
        videoView.setOnCompletionListener {
            videoView.start()
        }
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                if (!videoView.isPlaying) {
                    videoView.setVideoURI(Uri.parse(videoSource))
                    videoView.start()
                }
                delay(1000) // Проверяем каждую секунду
            }
        }


        val yt = findViewById<Button>(R.id.btnYT)
        val st = findViewById<Button>(R.id.btnST)
        val iptv = findViewById<Button>(R.id.btnIPTV)
        val tv = findViewById<Button>(R.id.btnTV)

        yt.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"))
            intent.setPackage("com.google.android.youtube.tv")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Обработка исключения, если приложение YouTube не установлено
            }
        }
        st.setOnClickListener {
            val launchIntent = packageManager.getLaunchIntentForPackage("com.teamsmart.videomanager.tv")
            if (launchIntent != null) {
                try {
                    startActivity(launchIntent)
                } catch (e: ActivityNotFoundException) {
                    // Обработка исключения, если приложение  не установлено
                }
            } else {
                Toast.makeText(this, "Приложение не найдено", Toast.LENGTH_SHORT).show()
            }
        }
        iptv.setOnClickListener {
            val launchIntent = packageManager.getLaunchIntentForPackage("com.ottplay.ottplay")
            if (launchIntent != null) {
                try {
                    startActivity(launchIntent)
                } catch (e: ActivityNotFoundException) {
                    // Обработка исключения, если приложение  не установлено
                }
            } else {
                Toast.makeText(this, "Приложение не найдено", Toast.LENGTH_SHORT).show()
            }
        }
        tv.setOnClickListener {
            val launchIntent = packageManager.getLaunchIntentForPackage("com.mediatek.wwtv.tvcenter")
            try {
                startActivity(launchIntent)
            } catch (e: ActivityNotFoundException) {
                // Обработка исключения, если приложение  не установлено
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val content = URL("http://10.10.0.5/info/info.txt").readText()
                withContext(Dispatchers.Main) {
                    marquee.text = content
                }
            } catch (e: Exception) {
                // Обработка ошибки
                withContext(Dispatchers.Main) {
                    marquee.text = "Произошла ошибка: ${e.message}"
                }
            }
        }

    }

    /*override fun onResume() {
        super.onResume()

    }*/

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return when (keyCode) {
//            KeyEvent.KEYCODE_DPAD_RIGHT -> {
//                // Здесь код для перехода на следующую активность
//                val intent = Intent(this, MainActivity3::class.java)
//                startActivity(intent)
//                true
//            }
//            else -> super.onKeyDown(keyCode, event)
//        }
//    }

}