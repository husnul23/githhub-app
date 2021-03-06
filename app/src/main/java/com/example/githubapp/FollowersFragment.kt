package com.example.githubapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_followers.*
import org.json.JSONArray
import java.lang.Exception

class FollowersFragment : Fragment() {

    companion object {
        private val ARG_USERNAME = "username"
        const val USER_TOKEN = "76804862f46181ed1aaa82cf10ca8c691193b982"
        private val TAG = DetailActivity::class.java.simpleName

        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var githubFollowers: RecyclerView
    private val listFollowersAdapter = FollowersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        githubFollowers = rv_followers
        githubFollowers.setHasFixedSize(true)

        val username = arguments?.getString(ARG_USERNAME)
        getfollowers(username)

        showRecycleList()
    }

    private fun getfollowers(username: String?) {
        val client = AsyncHttpClient()
        progressBarr.visibility = View.VISIBLE

        val url = "https://api.github.com/users/$username/followers"
        client.addHeader("Authorization", FollowersFragment.USER_TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                progressBarr.visibility = View.INVISIBLE
                val listFollowers = ArrayList<Github>()
                val result = String(responseBody)
                Log.d(FollowersFragment.TAG, result)
                try {
                    val items = JSONArray(result)

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val avatar = item.getString("avatar_url")
                        val username = item.getString("login")
                        val user = Github()

                        user.username = username
                        user.avatar = avatar
                        listFollowers.add(user)
                    }

                    listFollowersAdapter.listFollower = listFollowers

                    Log.d(FollowersFragment.TAG, "onSuccess: Selesai....")

                } catch (e: Exception) {
                    Log.d(FollowersFragment.TAG, "onSuccess: Gagal......")
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                Log.d(FollowersFragment.TAG, "onFailure: Gagal .....")
            }
        })
    }

    private fun showRecycleList() {
        githubFollowers.layoutManager = LinearLayoutManager(activity)
        githubFollowers.adapter = listFollowersAdapter
    }

}