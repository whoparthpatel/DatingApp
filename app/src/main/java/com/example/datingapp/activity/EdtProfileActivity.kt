package com.example.datingapp.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.SpinnerAdapter
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivityEdtProfileBinding
import java.util.Calendar

class EdtProfileActivity : ComponentActivity() {
    private lateinit var binding: ActivityEdtProfileBinding
    private lateinit var animation: Animation
    private val phonenumberregex = "[0-9]{10}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edt_profile)
        init()
        removerror()
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
        binding.signupBtn.setOnClickListener {
            validaton()
        }
        binding.dob.setOnClickListener {
            openDatePickerDialog()
        }
    }
    fun validaton() {
        var error = true
        if (binding.fullname.text?.isEmpty() == true) {
            binding.nameError.text = "It is required*"
            error = false
        }
        if (binding.dob.text?.isEmpty() == true) {
            binding.dobError.text = "It is required*"
            error = false
        }
        if (binding.phoneNumbert.text?.isEmpty() == true) {
            binding.phoneError.text = "It is required*"
            error = false
        }
        if (binding.gender.text?.isEmpty() == true) {
            binding.genderErroe.text = "It is required*"
            error = false
        }
        if (binding.hobbies.text?.isEmpty() == true) {
            binding.hobbiError.text = "It is required*"
            error = false
        }
        if (error) {
            animation = AnimationUtils.loadAnimation(this,R.anim.bounce)
            binding.signupBtn.startAnimation(animation)
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
    }
    fun removerror() {
        binding.fullname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding.nameError.text = ""
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
        binding.phoneNumbert.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding.phoneError.text = ""
                if (binding.phoneNumbert.text?.matches(phonenumberregex.toRegex()) == false) {
                    binding.phoneError.text = "Please Enter Valid Phone Number"
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
        binding.gender.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding.genderErroe.text = ""
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
        binding.hobbies.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding.hobbiError.text = ""
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
    }
    private fun openDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val minYear = 2003
        val maxYear = 2023

        val datePickerDialog = DatePickerDialog(
            this,R.style.DialogTheme,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                binding.dob.setText(selectedDate)
                val age =  calculateAge(year,month,dayOfMonth)
                binding.ageEdt.setText(age.toString())
            },
            currentYear,
            currentMonth,
            currentDay
        )

        val datePicker = datePickerDialog.datePicker
        datePicker.minDate = getDateInMillis(minYear, 1, 1)
        datePicker.maxDate = getDateInMillis(maxYear, 12, 31)

        datePickerDialog.show()
    }

    private fun getDateInMillis(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        return calendar.timeInMillis
    }
    fun calculateAge(birthYear: Int, birthMonth: Int, birthDay: Int): Int {
        val cal = Calendar.getInstance()
        val currentYear = cal.get(Calendar.YEAR)
        val currentMonth = cal.get(Calendar.MONTH)
        val currentDay = cal.get(Calendar.DAY_OF_MONTH)

        var age = currentYear - birthYear

        // Adjust age if the current date is before the birth date in the current year
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--
        }

        return age
    }
}