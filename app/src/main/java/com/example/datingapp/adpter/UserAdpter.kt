package com.example.datingapp.adpter

import android.graphics.drawable.AnimationDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.datingapp.R
import com.example.datingapp.model.UsersDataClass
import com.squareup.picasso.Picasso

class UserAdapter(private val userList: ArrayList<UsersDataClass>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var likeIcon: ImageView
    private lateinit var likeAnimation: ImageView
    private var isLiked = false

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userimg: ImageView = itemView.findViewById(R.id.user_img)
        val username: TextView = itemView.findViewById(R.id.user_name)
        val userbio: TextView = itemView.findViewById(R.id.user_bio)
        val userLikeContainer: FrameLayout = itemView.findViewById(R.id.likeContainer)
        val userLikeIcon : ImageView = itemView.findViewById(R.id.likeIcon)
        val userLikeAnimation : ImageView = itemView.findViewById(R.id.likeAnimation)

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
        Log.d("RESULTT", user.name.toString())
        Log.d("RESULTT", user.bio.toString())
        Log.d("RESULTT", user.dob.toString())
        Picasso.get()
            .load(url)
            .into(holder.userimg)
        holder.userLikeContainer.setOnClickListener {
            isLiked = !isLiked
            val iconRes = if (isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
            holder.userLikeIcon.setImageResource(iconRes)


            val animationDrawable = holder.userLikeAnimation.drawable as? AnimationDrawable
            animationDrawable?.let {
                it.stop()
                it.start()
            }

//            updateLikeButtonState()
//            playAnimation()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

//    private fun updateLikeButtonState() {
//        val iconRes = if (isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
//        likeIcon.setImageResource(iconRes)
//    }
//
//    private fun playAnimation() {
//        val animationDrawable = likeAnimation.drawable as? AnimationDrawable
//        animationDrawable?.let {
//            it.stop()
//            it.start()
//        }
//    }
}
