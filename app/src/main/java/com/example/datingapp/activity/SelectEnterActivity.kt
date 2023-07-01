package com.example.datingapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivitySelectEnterBinding


class SelectEnterActivity : ComponentActivity() {
    private lateinit var binding: ActivitySelectEnterBinding
    private lateinit var animation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animation = AnimationUtils.loadAnimation(this,R.anim.bounce)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_enter)
        init()
    }
    private fun init() {
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, com.example.datingapp.R.color.white)

        binding.loginBtn.setOnClickListener {
            binding.loginBtn.startAnimation(animation)
            Handler().postDelayed({
                 binding.rootLayout.visibility = View.GONE
            }, 520)
            Handler().postDelayed({
                val i = Intent(this,LogInActivity::class.java)
                startActivity(i)
                this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }, 2000)
        }
        binding.signupBtn.setOnClickListener {
            binding.signupBtn.startAnimation(animation)
            Handler().postDelayed({
                binding.rootLayout.visibility = View.GONE
            }, 520)
            Handler().postDelayed({
                val i = Intent(this,EdtProfileActivity::class.java)
                startActivity(i)
                this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }, 2000)
        }

    }
}