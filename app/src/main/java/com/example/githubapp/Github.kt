package com.example.githubapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Github (
    var username: String = "",
    var name: String = "",
    var avatar: Int = 0,
    var company: String = "",
    var repository: String = "",
    var followers: String = "",
    var followings: String = "",
    var location: String = ""
) : Parcelable