package com.example.githubapp

import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.database.MappingHelper
import com.example.githubapp.database.UserContract.UserColumns.Companion.CONTENT_URI
import com.example.githubapp.database.UserHelper
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.math.log

class UsersFavoritesActivity : AppCompatActivity() {

    private val listUsersFavoritesAdapter = ListUsersFavoritesAdapter().apply {
        setOnItemClickCallback(object : ListUsersFavoritesAdapter.OnItemClickCallback {
            override fun onItemClick(data: Github) {
//                showAlertDialog()
            }
        })
    }

    private lateinit var userHelper: UserHelper
    private var position: Int = 0
    private var github: Github? = null

    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
        const val EXTRA_USER = "extra_user"
        const val RESULT_DELETE = 301
        const val EXTRA_POSITION = "extra_position"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
        private lateinit var githubUsers: RecyclerView
        private val TAG = UsersFavoritesActivity::class.java.simpleName

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

        github = intent.getParcelableExtra(EXTRA_USER)
        initRecycleList()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadUsersAsync()
                Log.d(UsersFavoritesActivity.TAG, "Working oncreate")
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.deleteFav -> {
                showAlertDialog(ALERT_DIALOG_DELETE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadUsersAsync() {
        GlobalScope.launch(Dispatchers.Main) {
//            progressbar.visibility = View.VISIBLE
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                Log.d(UsersFavoritesActivity.TAG, "Working load")
                MappingHelper.mapCursorToArrayList(cursor)

            }
//            progressbar.visibility = View.INVISIBLE
            val users = deferredUsers.await()
            if (users.size > 0) {
                listUsersFavoritesAdapter.listUser = users
                Log.d(UsersFavoritesActivity.TAG, "Working load bawah banget")
            } else {
                listUsersFavoritesAdapter.listUser = ArrayList()
                Toast.makeText(this@UsersFavoritesActivity, "Data not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        val uriWithUsername = Uri.parse(CONTENT_URI.toString() + "/" + github?.username)

        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada favorite?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus favorite"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { dialog, id ->
                if (isDialogClose) {
                    finish()
                } else {
                    contentResolver.delete(uriWithUsername, null, null)
                    Toast.makeText(this, "berhasil dihapus", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun initRecycleList() {
        githubUsers.layoutManager = LinearLayoutManager(this)
        githubUsers.adapter = listUsersFavoritesAdapter
    }
}