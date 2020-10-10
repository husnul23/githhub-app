package com.example.githubapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    var listUser = listOf<Github>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    //inisiasi listener click
    fun setOnItemClickCallback(
        onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    //inisiasi layout untuk setiap view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val github = listUser[position]
        holder.bind(github, onItemClickCallback)
    }

    interface OnItemClickCallback {
        fun onItemClick(data: Github)
    }

    inner class ListViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView) {
        private var githubUsername: TextView = itemView.findViewById(R.id.tv_username)
        private var githubName: TextView = itemView.findViewById(R.id.tv_name)
        private var githubAvatar: ImageView = itemView.findViewById(R.id.img_item_photo)
        private var githubBtn: Button = itemView.findViewById(R.id.btn_details)

        fun bind(github: Github, clickListener: OnItemClickCallback) {
            githubBtn.setOnClickListener {
                clickListener.onItemClick(github)
            }

            Glide.with(itemView.context)
                .load(github.avatar)
                .apply(RequestOptions().override(60, 70))
                .into(githubAvatar)

            githubUsername.text = github.username
            githubName.text = github.name
        }
    }
}
