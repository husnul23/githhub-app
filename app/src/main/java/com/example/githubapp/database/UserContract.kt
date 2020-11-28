package com.example.githubapp.database

import android.provider.BaseColumns

class UserContract {
    class UserColumns: BaseColumns {
        companion object {
            val TABLE_NAME = "favorite_user"
            val _ID = "_id"
            val COLUMN_NAME_USERNAME = "username"
            val COLUMN_NAME_AVATAR_URL = "avatar_url"
            val COLUMN_NAME_COMPANY = "company"
            val COLUMN_NAME_LOCATION = "location"
            val COLUMN_NAME_FOLLOWINGS = "following"
            val COLUMN_NAME_FOLLOWERS = "followers"
            val COLUMN_NAME_REPOSITORY = "public_repos"
            val COLUMN_NAME_FULL_NAME = "name"
        }
    }
}