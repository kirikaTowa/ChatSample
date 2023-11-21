package com.example.chatexample.network.api

import com.example.chatexample.network.bean.MessageBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query


interface NetWorkService {
    @GET("api/test")
    fun getMessage(@Query("q") query: String): Call<MessageBean?>?

}
