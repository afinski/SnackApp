package com.zonar.snackapp.api

import retrofit2.Call
import retrofit2.http.GET

interface SnackApi {
    @GET("/")
    fun fetchContents(): Call<String>
}
