package com.example.datingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivityEdtProfileBinding

class EdtProfileActivity : ComponentActivity() {
    private lateinit var binding: ActivityEdtProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edt_profile)
        init()
    }
    private fun init() {
        binding.customeToolbar.title.text = "Profile"
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, com.example.datingapp.R.color.white)

        binding.customeToolbar.backBtn.setOnClickListener {
            val i = Intent(this,SplashScreen::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            finish()
        }

    }
}