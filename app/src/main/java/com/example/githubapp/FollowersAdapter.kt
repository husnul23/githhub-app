package com.example.githubapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    var listFollower = listOf<Github>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(
        onItemClickCallback: FollowersAdapter.OnItemClickCallback
    ) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_followers, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFollower.size
    }

    override fun onBindViewHolder(holder: FollowersAdapter.ListViewHolder, position: Int) {
        val github = listFollower[position]
        holder.bind(github)
    }

    interface OnItemClickCallback {
        fun onItemClick(data: Github)
    }

    inner class ListViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView) {
        private var githubUsername: TextView = itemView.findViewById(R.id.tv_follower_username)
        private var githubAvatar: ImageView = itemView.findViewById(R.id.img_follower_photo)

        fun bind(
            github: Github
        ) {

            Glide.with(itemView.context)
                .load(github.avatar)
                .apply(RequestOptions().override(60, 70))
                .into(githubAvatar)

            githubUsername.text = github.username
        }
    }
}