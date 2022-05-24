package com.example.myapplication.repository

import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.ApiGithub

class UserRepository {
    private val apiClient = ApiClient.getClient().create(ApiGithub::class.java)
    suspend fun getUsers(name : String) = apiClient.getUsers(name)
}