package com.lofrus.themoviedb

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.lofrus.themoviedb.utils.EspressoIdlingResource

class SplashScreenActivity : AppCompatActivity() {

    private val delayTimes: Long = 3500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        EspressoIdlingResource.increment()
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, delayTimes)
    }
}