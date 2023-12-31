package com.example.datingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivitySelectMakeProfileBinding

class SelectMakeProfileActivity : ComponentActivity() {
    private lateinit var binding: ActivitySelectMakeProfileBinding
    private lateinit var animation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_make_profile)
        animation = AnimationUtils.loadAnimation(this,R.anim.bounce)
        init()
    }
    private fun init() {
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, com.example.datingapp.R.color.white)

        binding.makeProfile.setOnClickListener {
            val fname = intent.getStringExtra("fname")
            val lname = intent.getStringExtra("lname")
            val email = intent.getStringExtra("email")
            val password = intent.getStringExtra("password")
            val repassword = intent.getStringExtra("repassword")
            binding.makeProfile.startAnimation(animation)
            Handler().postDelayed({
                binding.rootLayout.visibility = View.GONE
            }, 520)
            Handler().postDelayed({
                val i = Intent(this,EdtProfileActivity::class.java)
                i.putExtra("fname",fname)
                i.putExtra("lname",lname)
                i.putExtra("email",email)
                i.putExtra("password",password)
                i.putExtra("repassword",repassword)
                startActivity(i)
                this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }, 2000)
        }

    }
}