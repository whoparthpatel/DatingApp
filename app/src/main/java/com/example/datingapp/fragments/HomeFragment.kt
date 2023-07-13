package com.example.datingapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datingapp.R
import com.example.datingapp.adpter.UserAdapter
import com.example.datingapp.databinding.ActivityHomeBinding
import com.example.datingapp.databinding.FragmentHomeBinding
import com.example.datingapp.model.UsersDataClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    private lateinit var userAdapter: UserAdapter
    val userList = ArrayList<UsersDataClass>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv1)
        val loder : ConstraintLayout = view.findViewById(R.id.custome_loader)
        val root : FrameLayout = view.findViewById(R.id.root_layout)

            recyclerView.layoutManager = LinearLayoutManager(context)
            userAdapter = UserAdapter(userList)
            recyclerView.adapter = userAdapter

        val databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        userList.clear()
        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                root.visibility = View.GONE
                loder.visibility = View.VISIBLE
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UsersDataClass::class.java)
                    user?.let { userList.add(it) }
                    userAdapter.notifyDataSetChanged()
                    Log.d("RESULT",userList.toString())
                }
                root.visibility = View.VISIBLE
                loder.visibility = View.GONE
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"ERROR FOR DATABSE!",Toast.LENGTH_SHORT).show();
            }
        })


//
//        Handler().postDelayed({
//            root.visibility = View.VISIBLE
//            loder.visibility = View.GONE
//            recyclerView.layoutManager = LinearLayoutManager(context)
//            userAdapter = UserAdapter(userList)
//            recyclerView.adapter = userAdapter
//            retrieveDataFromFirebase()
//        }, 1500)



    }
//    private fun retrieveDataFromFirebase() {
//
//    }
}