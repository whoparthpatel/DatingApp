package com.example.datingapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivityLogInBinding
import com.example.datingapp.model.UsersDataClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener


class LogInActivity : ComponentActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var animation: Animation
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    private val specialchar = "(?=.*[@#$%^&+=])" + ".{4,}" + "$"
    private lateinit var dbRef : DatabaseReference

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        init()
        removeerror()
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, com.example.datingapp.R.color.white)
        binding.loginBtn.setOnClickListener {
            validation()
        }
        binding.forgotPassword.setOnClickListener {
            Toast.makeText(this, "it is working", Toast.LENGTH_SHORT).show()
        }
        binding.signupBtn.setOnClickListener {
            animation = AnimationUtils.loadAnimation(this,R.anim.bounce)
            binding.signupBtn.startAnimation(animation)
            Handler().postDelayed({
                binding.rootLayout.visibility = View.GONE
            }, 520)
            Handler().postDelayed({
                val i = Intent(this,SignUpActivity::class.java)
                startActivity(i)
                this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }, 2000)
        }
        binding.customeToolbar.backBtn.setOnClickListener {
            val i = Intent(this, SelectEnterActivity::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }
    fun validation() {
        var error = true
        if (binding.emailEdt.text?.isEmpty() == true) {
            binding.EmailError.text = "It is required*"
            error = false
        }
        if (binding.passEdt.text?.isEmpty() == true) {
            binding.PassError.text = "It is required*"
            error = false
        }
         if (error) {
             val email = binding.emailEdt.text.toString()
             val password = binding.passEdt.text.toString()
             var flag = true
             dbRef =  FirebaseDatabase.getInstance().getReference("Users")
             dbRef.orderByChild("emali").equalTo(email).addListenerForSingleValueEvent(object :
                 ValueEventListener {
                 override fun onDataChange(dataSnapshot: DataSnapshot) {
                     if (dataSnapshot.exists()) {
                         for (ds in dataSnapshot.children) {
                             val user = ds.getValue(UsersDataClass::class.java)
                             if (password.equals(user?.password)) {
                                 flag = false
                                 animation =
                                     AnimationUtils.loadAnimation(applicationContext, R.anim.bounce)
                                 binding.loginBtn.startAnimation(animation)
                                 Handler().postDelayed({
                                     binding.rootLayout.visibility = View.GONE
                                 }, 520)
                                 Handler().postDelayed({
                                     val i = Intent(applicationContext, HomeActivity::class.java)
                                     val id = user?.userId
                                     i.putExtra("userid",id)
                                     startActivity(i)
                                     overridePendingTransition(
                                         R.anim.slide_in_right,
                                         R.anim.slide_out_left
                                     )
                                     finish()
                                 }, 2000)
                             } else {
                                 binding.EmailError.text = "Please enter valid email"
                                 binding.PassError.text = "Please enter valid password"
                             }
                         }
                     } else {
                         binding.EmailError.text = "Please enter valid email"
                         binding.PassError.text = "Please enter valid password"
                     }

                 }
                 override fun onCancelled(databaseError: DatabaseError) {
                     // Handle database error
                     println("Database error: ${databaseError.message}")
                     Toast.makeText(applicationContext,"bYYY ",Toast.LENGTH_SHORT).show();

                 }
             })
        }
    }
    fun removeerror() {
        binding.emailEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding.EmailError.text = ""
                if (binding.emailEdt.text?.matches(emailRegex.toRegex()) == false) {
                    binding.EmailError.text = "Please Enter Valid Email"
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
        binding.passEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding.PassError.text = ""
                if (binding.passEdt.text?.matches(specialchar.toRegex()) == false) {
                    binding.PassError.text = "Please Enter Valid Password"
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
    }
}