package com.example.githubapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailUserAdapter : RecyclerView.Adapter<DetailUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    var listUser = listOf<Github>()
    set(value)  {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.detail_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: DetailUserAdapter.ListViewHolder, position: Int) {
        val github = listUser[position]
        holder.bind(github)
    }

    interface OnItemClickCallback {
        fun onItemClick(data: Github)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var githubUsername: TextView = itemView.findViewById(R.id.tv_detail_username)
        private var githubName: TextView = itemView.findViewById(R.id.tv_detail_name)
        private var githubAvatar: ImageView = itemView.findViewById(R.id.imgProfilePict)
        private var githubLocation: TextView = itemView.findViewById(R.id.tv_detail_location)
        private var githubFollowers: TextView = itemView.findViewById(R.id.tv_detail_followers)
        private var githubFollowings: TextView = itemView.findViewById(R.id.tv_detail_followings)
        private var githubRepository: TextView = itemView.findViewById(R.id.tv_detail_repository)

        fun bind(github: Github) {
            githubUsername.text = github.username
            githubName.text = github.name
            githubLocation.text = github.location
            githubFollowings.text = github.followings
            githubFollowers.text = github.followers
            githubRepository.text = github.repository

            Glide.with(itemView.context)
                .load(github.avatar)
                .apply(RequestOptions().override(60, 60))
                .into(githubAvatar)

        }
    }
}