package com.dgioto.beatbox

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 5000 // Задержка в N секунд

    private val splashHandler = Handler()
    private val splashRunnable = Runnable {
        val intent = Intent(this@StartActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val version: TextView = findViewById(R.id.version)
        //app version
        val appVersionName = BuildConfig.VERSION_NAME
        version.setText(appVersionName)

        // Запускаем MainActivity после паузы
        splashHandler.postDelayed(splashRunnable, SPLASH_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Очищаем задачи из Handler для предотвращения утечки памяти
        splashHandler.removeCallbacks(splashRunnable)
    }
}