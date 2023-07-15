package com.dgioto.beatbox

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val SPLASH_DELAY: Long = 5000 // Задержка в N секунд

class StartActivity : AppCompatActivity() {

//    private lateinit var beatBoxLoad: BeatBoxLoad
    private lateinit var progressBar: ProgressBar

    private val splashHandler = Handler()
    private val splashRunnable = Runnable {
        progressBar.visibility = View.GONE // Скрыть ProgressBar
        val intent = Intent(this@StartActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Инициализация ProgressBar
        progressBar = findViewById(R.id.progress_bar)

        val version: TextView = findViewById(R.id.version)
        //app version
        val appVersionName = BuildConfig.VERSION_NAME
        version.text = appVersionName

//        beatBoxLoad = BeatBoxLoad(assets)

        // Показать ProgressBar
        progressBar.visibility = View.VISIBLE

        // Запускаем MainActivity после паузы
        splashHandler.postDelayed(splashRunnable, SPLASH_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Очищаем задачи из Handler для предотвращения утечки памяти
        splashHandler.removeCallbacks(splashRunnable)
    }
}