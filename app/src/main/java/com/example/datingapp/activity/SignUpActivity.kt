package com.example.datingapp.activity

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivitySignUpBinding
import com.example.datingapp.model.UsersDataClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignUpActivity : ComponentActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var animation: Animation
    private lateinit var password : String
    private lateinit var dbRef : DatabaseReference
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    private val specialchar = "(?=.*[@#$%^&+=])" + ".{4,}" + "$"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        init()
        removerror()
    }
    private fun init() {
        binding.customeToolbar.title.text = "Sign Up"
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, com.example.datingapp.R.color.white)

        binding.loginTxt.setOnClickListener {
            val i = Intent(this,LogInActivity::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }

        binding.signupBtn.setOnClickListener {
            validaton()
        }
        binding.customeToolbar.backBtn.setOnClickListener {
            val i = Intent(this,SelectEnterActivity::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }
    fun validaton() {
        var error = true
        if (binding.firstEdt.text?.isEmpty() == true) {
            binding.firstnameError.text = "It is required*"
            error = false
        }
        if (binding.lastEdt.text?.isEmpty() == true) {
            binding.lastnameError.text = "It is required*"
            error = false
        }
        if (binding.emailEdt.text?.isEmpty() == true) {
            binding.mailError.text = "It is required*"
            error = false
        }
        if (binding.passEdt.text?.isEmpty() == true) {
            binding.passError.text = "It is required*"
            error = false
        }
        if (binding.repassEdt.text?.isEmpty() == true) {
            binding.repassError.text = "It is required*"
            error = false
        }
        if (error) {
            hideKeyboard()
            val fname = binding.firstEdt.text.toString()
            val lname = binding.lastEdt.text.toString()
            val email = binding.emailEdt.text.toString()
            val password = binding.passEdt.text.toString()
            val repassword = binding.repassEdt.text.toString()
            dbRef =  FirebaseDatabase.getInstance().getReference("Users")
            val query = dbRef.orderByChild("emali").equalTo(email)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        binding.mailError.text = "Email is already exists"
                    } else {
                        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce)
                        binding.signupBtn.startAnimation(animation)
                        Handler().postDelayed({
                            binding.rootLayout.visibility = View.GONE
                        }, 520)
                        Handler().postDelayed({
                            val i = Intent(applicationContext, SelectMakeProfileActivity::class.java)
                            i.putExtra("fname",fname)
                            i.putExtra("lname",lname)
                            i.putExtra("email",email)
                            i.putExtra("password",password)
                            i.putExtra("repassword",repassword)
                            startActivity(i)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            finish()
                        }, 2000)
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors that may occur during the query
                    Log.e(TAG, "Error querying database: ${databaseError.message}")
                }
            })

        }
    }
    fun removerror() {
        binding.firstEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding.firstnameError.text = ""
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
        binding.lastEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding.lastnameError.text = ""
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
        binding.emailEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding.mailError.text = ""
                if (binding.emailEdt.text?.matches(emailRegex.toRegex()) == false) {
                    binding.mailError.text = "Please Enter Valid Email"
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
        binding.passEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                password = binding.passEdt.text.toString()
                binding.passError.text = ""
                if (binding.passEdt.text?.matches(specialchar.toRegex()) == false) {
                    binding.passError.text = "Please Enter Valid Password"
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
        binding.repassEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding.repassError.text = ""
                if (binding.repassEdt.text?.matches(password.toRegex()) == false) {
                    binding.repassError.text = "Do not match password"
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
    }
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = currentFocus
        if (currentFocusView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
        }
    }
}



