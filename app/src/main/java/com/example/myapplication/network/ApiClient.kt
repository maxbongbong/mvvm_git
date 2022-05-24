package com.example.myapplication.network

import com.example.myapplication.BuildConfig
import com.example.myapplication.util.CommonUtils
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {

        const val BASIC_URL = "https://api.github.com/"

        // Retrofit client
        @JvmStatic
        fun getClient(): Retrofit = retrofit

        private val retrofit: Retrofit by lazy {

            val clientBuilder = CommonUtils().getUnsafeOkHttpClient()
            setOkHttpClient(clientBuilder)

            Retrofit.Builder()
                .client(clientBuilder.build())
                .baseUrl(BASIC_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonFactory())
                .build()
        }

        private fun gsonFactory() : GsonConverterFactory {
            val gson = GsonBuilder().setLenient().create()
            return GsonConverterFactory.create(gson)
        }

        private fun getLogInterceptor(): HttpLoggingInterceptor {

            val logging = HttpLoggingInterceptor()

            if(BuildConfig.DEBUG) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                logging.setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            return logging
        }

        private fun setOkHttpClient(builder: OkHttpClient.Builder){

            builder
                .addInterceptor(getLogInterceptor())
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
        }
    }
}