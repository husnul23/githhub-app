package com.example.githubapp.database

import android.database.Cursor
import com.example.githubapp.Github

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<Github> {
        val userList = ArrayList<Github>()

        userCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_USERNAME))
                val avatar = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_AVATAR_URL))
                userList.add(Github(username = username, avatar = avatar))
            }
        }
        return userList
    }

    fun mapCursorToObject(notesCursor: Cursor?): Github {
        var github = Github()
        notesCursor?.apply {
            moveToFirst()
            val username = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_USERNAME))
            val avatar = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_AVATAR_URL))
            github = Github(username = username, avatar = avatar)
        }
        return github
    }
}