package com.example.datingapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivityLogInBinding
class LogInActivity : ComponentActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var animation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        init()
    }

    private fun init() {
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, com.example.datingapp.R.color.white)

        binding.loginBtn.setOnClickListener {
//            Toast.makeText(this, "it is working", Toast.LENGTH_SHORT).show()
//                val i = Intent(this,MainActivity::class.java)
//                startActivity(i)
//                this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//                finish()
            animation = AnimationUtils.loadAnimation(this,R.anim.bounce)
            binding.loginBtn.startAnimation(animation)
        }
        binding.forgotPassword.setOnClickListener {
            Toast.makeText(this, "it is working", Toast.LENGTH_SHORT).show()
        }
        binding.signupBtn.setOnClickListener {
//            val i = Intent(this, SignUpActivity::class.java)
//            startActivity(i)
//            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            finish()
            animation = AnimationUtils.loadAnimation(this,R.anim.bounce)
            binding.signupBtn.startAnimation(animation)
        }
        binding.customeToolbar.backBtn.setOnClickListener {
            val i = Intent(this, SelectEnterActivity::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }
}