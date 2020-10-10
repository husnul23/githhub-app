package com.example.githubapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var githubUsers: RecyclerView
    private var  list: ArrayList<Github> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        githubUsers = findViewById(R.id.rv_github)
        githubUsers.setHasFixedSize(true)

        list.addAll(UserData.listData)
        showRecycleList()
    }

    private fun showRecycleList() {
        githubUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter().apply {
            listUser = list
            setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClick(data: Github) {
                    navigateToDetail(data)
                }
            })
        }
        githubUsers.adapter = listUserAdapter
    }

    fun navigateToDetail(github: Github) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_GITHUB, github)
        }
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}