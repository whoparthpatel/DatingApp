package com.example.datingapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivityHomeBinding
import com.example.datingapp.fragments.HomeFragment
import com.example.datingapp.fragments.NotificationFragment
import com.example.datingapp.fragments.ProfileFragment
import com.example.datingapp.fragments.SmsFragment
import com.example.datingapp.model.UsersDataClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_home)
        init()
        binding.customeToolbar.title.text = "Home"
        loadFragment(HomeFragment())
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    binding.customeToolbar.title.text = "Home"
                    true
                }
                R.id.message -> {
                    loadFragment(SmsFragment())
                    binding.customeToolbar.title.text = "Message"
                        true
                }

                R.id.noti -> {
                    loadFragment(NotificationFragment())
                    binding.customeToolbar.title.text = "Notification"
                    true
                }

                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    binding.customeToolbar.title.text = "Profile"
                    true
                }

                else -> {
                    false
                }
            }
        }
    }
    private fun init() {
        val id = intent.getStringExtra("userid")
        Log.d("USECASE",id.toString())
        binding.customeToolbar.backBtn.visibility = View.GONE
        val window: Window = this.window
        window.clearFlags(FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        binding.customeToolbar.logoutBtn.setOnClickListener {
            binding.rootLayout.visibility = View.GONE
            Handler().postDelayed({
                saveUserLoggedInState(false)
                val i = Intent(this, LogInActivity::class.java)
                startActivity(i)
                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
                finish()
            }, 1500)
        }
    }

    private fun saveUserLoggedInState(isLoggedIn: Boolean){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }
//    private fun retriveData() {
//        dbRef =  FirebaseDatabase.getInstance().getReference("Users")
//        val idReference = id?.let { dbRef.child(it) }
//        idReference?.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val value = dataSnapshot.getValue(UsersDataClass::class.java)
//                val dob =  value?.dob
//                Toast.makeText(applicationContext,dob, Toast.LENGTH_SHORT).show()
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fram_in,fragment)
        transaction.commit()
    }

}