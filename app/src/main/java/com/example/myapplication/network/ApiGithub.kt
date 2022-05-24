package com.example.myapplication.network

import com.example.myapplication.data.UserItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGithub {
    @GET(ApiClient.BASIC_URL + "search/users")
    suspend fun getUsers(
        @Query("q") name : String
    ) : Response<UserItem>
}