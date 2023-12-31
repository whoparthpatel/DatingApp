package com.example.datingapp.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivitySplashScreenBinding



@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (!isLoggedIn) {
            Handler().postDelayed({
                newActivity()
            }, 2000)
        } else {
            Handler().postDelayed({
                homeact()
            }, 2000)
        }
        init()
    }
    private fun init() {
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, com.example.datingapp.R.color.white)


    }
    private fun newActivity() {
        val i = Intent(this,LogInActivity::class.java)
        startActivity(i)
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
    private fun homeact() {
        val i = Intent(this,HomeActivity::class.java)
        startActivity(i)
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
}