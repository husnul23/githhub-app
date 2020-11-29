package com.example.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.database.MappingHelper
import com.example.githubapp.database.UserHelper
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UsersFavoritesActivity : AppCompatActivity() {

    private val listUsersFavoritesAdapter = ListUsersFavoritesAdapter()
    private lateinit var userHelper: UserHelper

    companion object{
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FAVORITE = "extra_favorite"
        const val EXTRA_POSITION = "extra_position"
        private lateinit var githubUsers: RecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_favorites)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar?.elevation = 0f

        githubUsers = findViewById(R.id.rvUserFavorite)
        githubUsers.setHasFixedSize(true)

        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()

        initRecycleList()
        loadUsersAsync()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadUsersAsync() {
        GlobalScope.launch(Dispatchers.Main) {
//            progressbar.visibility = View.VISIBLE
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = userHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
//            progressbar.visibility = View.INVISIBLE
            val users = deferredUsers.await()
            if (users.size > 0) {
                listUsersFavoritesAdapter.listUser = users
            } else {
                listUsersFavoritesAdapter.listUser = ArrayList()
                Toast.makeText(this@UsersFavoritesActivity, "Data not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecycleList() {
        githubUsers.layoutManager = LinearLayoutManager(this)
        githubUsers.adapter = listUsersFavoritesAdapter
    }
}