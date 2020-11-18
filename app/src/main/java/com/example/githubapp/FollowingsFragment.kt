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
import kotlinx.android.synthetic.main.fragment_followings.*
import org.json.JSONArray
import java.lang.Exception

class FollowingsFragment : Fragment() {

    companion object {
        private val ARG_USERNAME = "username"
        const val USER_TOKEN = "76804862f46181ed1aaa82cf10ca8c691193b982"
        private val TAG = DetailActivity::class.java.simpleName

        fun newInstance(username: String): FollowingsFragment {
            val fragment = FollowingsFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var githubFollowings: RecyclerView
    private val listFollowingsAdapter = FollowingsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        githubFollowings = rv_followings
        githubFollowings.setHasFixedSize(true)

        val username = arguments?.getString(ARG_USERNAME)
        getfollowings(username)

        showRecycleList()
    }

    private fun getfollowings(username: String?) {
        val client = AsyncHttpClient()
        progressBarrr.visibility = View.VISIBLE

        val url = "https://api.github.com/users/$username/following"
        client.addHeader("Authorization", FollowingsFragment.USER_TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                progressBarrr.visibility = View.INVISIBLE
                val listFollowings = ArrayList<Github>()
                val result = String(responseBody)
                Log.d(FollowingsFragment.TAG, result)
                try {
                    val items = JSONArray(result)

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val avatar = item.getString("avatar_url")
                        val username = item.getString("login")
                        val user = Github()

                        user.username = username
                        user.avatar = avatar
                        listFollowings.add(user)
                    }

                    listFollowingsAdapter.listFollowings = listFollowings

                    Log.d(FollowingsFragment.TAG, "onSuccess: Selesai....")

                } catch (e: Exception) {
                    Log.d(FollowingsFragment.TAG, "onSuccess: Gagal......")
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                Log.d(FollowingsFragment.TAG, "onFailure: Gagal .....")
            }
        })
    }

    private fun showRecycleList() {
        githubFollowings.layoutManager = LinearLayoutManager(activity)
        githubFollowings.adapter = listFollowingsAdapter
    }
}