package com.example.datingapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datingapp.R
import com.example.datingapp.adpter.UserAdapter
import com.example.datingapp.databinding.FragmentHomeBinding
import com.example.datingapp.databinding.FragmentProfileBinding
import com.example.datingapp.model.UsersDataClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {
    private lateinit var dbRef : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = activity?.intent!!.getStringExtra("userId")
        Log.d("USECASE",id.toString())
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
}