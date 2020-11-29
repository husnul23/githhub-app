package com.example.githubapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapp.database.UserContract
import com.example.githubapp.database.UserHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    lateinit var githubUsername: TextView
    lateinit var githubAvatar: ImageView

    lateinit var context: Context


    var listUser = listOf<Github>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(
        onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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

    fun bindData(context: Context) {
        this.context = context
    }

    inner class ListViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView) {
        private lateinit var userHelper: UserHelper
        private var githubUsername: TextView = itemView.findViewById(R.id.tv_username)
        private var githubName: TextView = itemView.findViewById(R.id.tv_name)
        private var githubAvatar: ImageView = itemView.findViewById(R.id.img_item_photo)
        private var githubBtn: Button = itemView.findViewById(R.id.btn_details)
        var floatingVaf: FloatingActionButton = itemView.findViewById(R.id.floatingFav)
        var floatingUnfav: FloatingActionButton = itemView.findViewById(R.id.floatingUnfav)

        init {
            userHelper = UserHelper(context)
            userHelper.open()
        }

        fun bind(github: Github, clickListener: OnItemClickCallback) {
            githubBtn.setOnClickListener {
                clickListener.onItemClick(github)
            }

            var statusFavorite = false

            floatingVaf.setOnClickListener {
                statusFavorite = true
                setStatusFavorite(statusFavorite, it)
            }

            floatingUnfav.setOnClickListener {
                val values = ContentValues()
                values.put(UserContract.UserColumns.COLUMN_NAME_USERNAME, githubUsername.text.toString())
                values.put(UserContract.UserColumns.COLUMN_NAME_AVATAR_URL, github.avatar)
                userHelper.insert(values)
                statusFavorite = false
                setStatusFavorite(statusFavorite, it)
            }

            Glide.with(itemView.context)
                .load(github.avatar)
                .apply(RequestOptions().override(60, 70))
                .into(githubAvatar)

            githubUsername.text = github.username
            githubName.text = github.name
        }

        @SuppressLint("RestrictedApi")
        private fun setStatusFavorite(statusFavorite: Boolean, view: View) {
            if (statusFavorite) {
                floatingVaf.visibility = View.INVISIBLE
                floatingUnfav.visibility = View.VISIBLE
            } else {
                floatingVaf.visibility = View.VISIBLE
                floatingUnfav.visibility = View.INVISIBLE
            }
        }
    }
}
