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

class FollowingsFragment : Fragment() {

    companion object {
        private val ARG_USERNAME = "username"
        const val USER_TOKEN = "76804862f46181ed1aaa82cf10ca8c691193b982"
        private val TAG = FollowingsFragment::class.java.simpleName
    }

    fun newInstance(username: String): FollowingsFragment {
        val fragment = FollowingsFragment()
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
        return inflater.inflate(R.layout.fragment_followings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        TODO("akses API dari getfollowings method")
        val username = arguments?.getString(FollowingsFragment.ARG_USERNAME)
        super.onViewCreated(view, savedInstanceState)
        getfollowings(username)
    }

    private fun getfollowings(username: String?) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
        client.addHeader("Authorization", FollowingsFragment.USER_TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(FollowingsFragment.TAG, result)
                try {
                    val item = JSONObject(result)

                    val followingsList = item.getString("followings_url")
                    val user = Github()
                    user.followings = followingsList
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
                progressBar.visibility = View.INVISIBLE
                Log.d(FollowingsFragment.TAG, "onFailure: Gagal .....")
            }
        })
    }
}