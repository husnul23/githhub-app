package com.example.githubapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class FollowersFragment : Fragment() {

    companion object {
        private val ARG_USERNAME = "username"
        const val USER_TOKEN = "76804862f46181ed1aaa82cf10ca8c691193b982"
        private val TAG = DetailActivity::class.java.simpleName
    }

    fun newInstance(username: String): FollowersFragment {
        val fragment = FollowersFragment()
        val bundle = Bundle()
        bundle.putString(ARG_USERNAME, username)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        TODO("akses API dari method getfollowers")

        val username = arguments?.getString(ARG_USERNAME)
        getfollowers(username)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getfollowers(username: String?) {
        val client = AsyncHttpClient()

//        progressBar.visibility = View.VISIBLE
        val url = "https://api.github.com/search/users?q=$username"
        client.addHeader("Authorization", FollowersFragment.USER_TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(FollowersFragment.TAG, result)
                try {
                    val item = JSONObject(result)

                    val followersList = item.getString("followers_url")
                    val user = Github()
                    user.followers = followersList
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
//                progressBar.visibility = View.INVISIBLE
                Log.d(FollowersFragment.TAG, "onFailure: Gagal .....")
            }
        })
    }

}