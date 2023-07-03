package com.example.datingapp.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivityEdtProfileBinding
import com.example.datingapp.model.UsersDataClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.Calendar

class EdtProfileActivity : ComponentActivity() {
    private lateinit var binding: ActivityEdtProfileBinding
    private lateinit var animation: Animation
    private lateinit var uri : Uri
    private val phonenumberregex = "[0-9]{10}"
    private lateinit var dbRef : DatabaseReference
    private var storage = Firebase.storage

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

        storage = FirebaseStorage.getInstance()

        val gallaryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.profileImage.setImageURI(it)
                if (it != null) {
                    uri = it
                } else {
                    Toast.makeText(this,"Please Select Image",Toast.LENGTH_SHORT).show()
                }
            }
        )

        binding.imgChoise.setOnClickListener {
            gallaryImage.launch("image/*")
        }

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this,R.layout.drop_item, gender)
        binding.gender.setAdapter(arrayAdapter)

        binding.customeToolbar.backBtn.setOnClickListener {
            val i = Intent(this,SplashScreen::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            finish()
        }
        binding.SaveProfile.setOnClickListener {
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
            hideKeyboard()
            animation = AnimationUtils.loadAnimation(this,R.anim.bounce)
            binding.SaveProfile.startAnimation(animation)
            val fname = intent.getStringExtra("fname")
            val lname = intent.getStringExtra("lname")
            val email = intent.getStringExtra("email")
            val password = intent.getStringExtra("password")
            val repassword = intent.getStringExtra("repassword")
            val name = binding.fullname.text.toString()
            val dob = binding.dob.text.toString()
            val phone = binding.phoneNumbert.text.toString()
            val gender = binding.gender.text.toString()
            val age = binding.ageEdt.text.toString()
            val bio = binding.hobbies.text.toString()
            storage = FirebaseStorage.getInstance()
            storage.getReference("UserImages").child(System.currentTimeMillis().toString())
                .putFile(uri)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            dbRef = FirebaseDatabase.getInstance().getReference("Users")
                            val userId = dbRef.push().key!!
                            val user = UsersDataClass(
                                userId,
                                fname,
                                lname,
                                email,
                                password,
                                repassword,
                                name,
                                dob,
                                phone,
                                gender,
                                age,
                                bio,
                                it.toString()
                            )
                            dbRef.child(userId).setValue(user)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        this,
                                        "DATA SAVE SUCCESFULLY",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "ERROR{${it.message}}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            Handler().postDelayed({
                                binding.rootLayout.visibility = View.GONE
                            }, 520)
                            Handler().postDelayed({
                                val i = Intent(this, LogInActivity::class.java)
                                startActivity(i)
                                this.overridePendingTransition(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left
                                )
                                finish()
                            }, 2000)
                        }
                }.addOnFailureListener {
                    Toast.makeText(applicationContext, "FAIL", Toast.LENGTH_SHORT).show()
                }
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

        val minYear = 1990
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
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = currentFocus
        if (currentFocusView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
        }
    }
}