package com.wendreof.retrofit.service

import com.wendreof.model.Product
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {
    @GET("products")
    fun list(): Call<List<Product>>

    @POST("products")
    fun insert(@Body product: Product): Call<Product>
}
