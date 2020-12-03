package com.example.githubapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListUsersFavoritesAdapter : RecyclerView.Adapter<ListUsersFavoritesAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    var listUser = listOf<Github>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(
        onItemClickCallback: OnItemClickCallback
    ) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_favorites, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListUsersFavoritesAdapter.ListViewHolder, position: Int) {
        val github = listUser[position]
        holder.bind(github, onItemClickCallback)
    }

    interface OnItemClickCallback {
        fun onItemClick(data: Github)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var githubUsername: TextView = itemView.findViewById(R.id.tvUserFavorite)
        private var githubAvatar: ImageView = itemView.findViewById(R.id.userFavAvatar)
        private var deleteFavorite: Button = itemView.findViewById(R.id.deleteFav)

        fun bind(github: Github, clickListener: OnItemClickCallback) {
            deleteFavorite.setOnClickListener {
                clickListener.onItemClick(github)
            }

            Glide.with(itemView.context)
                .load(github.avatar)
                .apply(RequestOptions().override(60, 70))
                .into(githubAvatar)

            githubUsername.text = github.username
        }
    }
}