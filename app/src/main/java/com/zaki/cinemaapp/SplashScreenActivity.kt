package com.zaki.cinemaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.zaki.cinemaapp.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {

    private var _handler: Handler? = null
    private val handler get() = _handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        _handler = Handler(Looper.getMainLooper())
        handler?.postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()
        _handler = null
    }

    companion object {
        const val TIME_DELAY = 3000L
    }
}