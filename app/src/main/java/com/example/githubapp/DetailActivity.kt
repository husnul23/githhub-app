package com.example.githubapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.detail_user.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONObject
import java.lang.Exception

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_GITHUB = "extra_github"
        const val USER_TOKEN = "76804862f46181ed1aaa82cf10ca8c691193b982"
        private val TAG = DetailActivity::class.java.simpleName
    }

    private val detailUserAdapter = DetailUserAdapter()

    lateinit var github: Github

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_user)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val user = Github()
        sectionsPagerAdapter.username = user?.username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar?.elevation = 0f

        github = intent.getParcelableExtra(EXTRA_GITHUB)!!

        getUserDetail(github.username)

    }

    private fun getUserDetail(username: String?) {
        val client = AsyncHttpClient()

        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", DetailActivity.USER_TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)

                Log.d(DetailActivity.TAG, result)
                try {

                    val item = JSONObject(result)

                    val username = item.getString("login")
                    val avatar = item.getString("avatar_url")
                    val followersList = item.getString("followers")
                    val followingsList = item.getString("following")
                    val location = item.getString("location")
                    val company = item.getString("company")
                    val fullName = item.getString("name")
                    val totalRepos = item.getString("public_repos")

                    val user = Github()
                    user.username = username
                    user.avatar = avatar
                    user.followers = followersList
                    user.followings = followingsList
                    user.location = location
                    user.company = company
                    user.name = fullName
                    user.repository = totalRepos

                    tv_detail_username.text = user.username
                    tv_detail_name.text = user.name
                    tv_detail_followers.text = user.followers
                    tv_detail_followings.text = user.followings
                    tv_detail_company.text = user.company
                    tv_detail_repository.text = user.repository
                    tv_detail_location.text = user.location


                    Glide.with(this@DetailActivity)
                        .load(user.avatar)
                        .apply(RequestOptions().override(100, 100))
                        .into(imgProfilePict)

                } catch (e: Exception) {
                    Log.d(TAG, "onSuccess: Gagal......")
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {

                Log.d(TAG, "onFailure: Gagal .....")
            }
        })
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
