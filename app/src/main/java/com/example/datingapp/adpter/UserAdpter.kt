package com.example.datingapp.adpter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datingapp.R
import com.example.datingapp.model.UsersDataClass
import com.squareup.picasso.Picasso

class UserAdapter(private val userList: ArrayList<UsersDataClass>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userimg : ImageView = itemView.findViewById(R.id.user_img)
        val username : TextView = itemView.findViewById(R.id.user_name)
        val userbio : TextView = itemView.findViewById(R.id.user_bio)
        val userlike : ImageView = itemView.findViewById(R.id.like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.username.text = user.name
        holder.userbio.text = user.bio
        val url = user.imgUrl
        Log.d("RESULTT",user.name.toString())
        Log.d("RESULTT",user.bio.toString())
        Log.d("RESULTT",user.dob.toString())
        Picasso.get()
            .load(url)
            .into(holder.userimg)
    }
    override fun getItemCount(): Int {
        return userList.size
    }
}
