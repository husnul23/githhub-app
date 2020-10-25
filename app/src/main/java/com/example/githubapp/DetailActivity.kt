package com.example.githubapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.detail_user.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONObject
import java.lang.Exception

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_GITHUB = "extra_github"
        private val TAG = DetailActivity::class.java.simpleName
        const val USER_TOKEN = "76804862f46181ed1aaa82cf10ca8c691193b982"
    }

    lateinit var github: Github

    private fun searchUsers(username: String?) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q={$username}"
        client.addHeader("Authorization", DetailActivity.USER_TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val listUser = ArrayList<Github>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = Github()

                        user.username = username
                        user.avatar = avatar
                        listUser.add(user)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "onFailure: Gagal .....")
            }
        })
    }

    private fun getfollowers(username: String?) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
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

                    val followersList = item.getString("followers_url")
                    val user = Github()
                    user.followers = followersList
                    Log.d(TAG, "onSuccess: Selesai....")

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

    private fun getUserDetail(username: String?) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
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
                    val followersList = item.getString("followers_url")
                    val followingsList = item.getString("followings_url")
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

                    Log.d(TAG, "onSuccess: Selesai....")

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

private fun CircleImageView.setImageResource(avatar: String) {

}
