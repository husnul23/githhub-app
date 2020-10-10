package com.example.githubapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.detail_user.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_GITHUB = "extra_github"
    }

    lateinit var github: Github

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_user)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        github = intent.getParcelableExtra(EXTRA_GITHUB)!!

        val githubName = findViewById<TextView>(R.id.tv_detail_name)
        val githubUsername = findViewById<TextView>(R.id.tv_detail_username)
        val githubLocation = findViewById<TextView>(R.id.tv_detail_location)
        val githubFollowers = findViewById<TextView>(R.id.tv_detail_followers)
        val githubFollowings = findViewById<TextView>(R.id.tv_detail_followings)
        val githubCompany = findViewById<TextView>(R.id.tv_detail_company)
        val githubRepository = findViewById<TextView>(R.id.tv_detail_repository)

        githubName.text = github.name
        githubUsername.text = github.username
        githubLocation.text = github.location
        githubFollowers.text = github.followers
        githubFollowings.text = github.followings
        githubCompany.text = github.company
        githubRepository.text = github.repository
        imgProfilePict.setImageResource(github.avatar)

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