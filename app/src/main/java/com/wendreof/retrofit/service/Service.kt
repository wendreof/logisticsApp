package com.wendreof.retrofit.service

import com.wendreof.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface Service {
    @GET("products")
    fun list(): Call<List<Product>>
}