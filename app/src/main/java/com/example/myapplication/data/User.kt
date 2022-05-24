package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("items")
    val items: List<User>
)

data class User(
    @SerializedName("id")
    val id: Long? = 0,
    @SerializedName("login")
    val name: String? = null,
    @SerializedName("html_url")
    val htmlUrl: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null
)


