package com.wendreof.retrofit.service

import com.wendreof.model.Product
import retrofit2.Call
import retrofit2.http.*

interface Service
{
    @GET("products")
    fun list(): Call<List<Product>>

    @POST("products")
    fun insert(@Body product: Product): Call<Product>

    @PUT("products/{id}")
    fun alter(@Body procuct: Product, @Path("id") id: Int): Call<Product>
}
