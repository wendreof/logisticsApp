package com.wendreof.retrofit

import com.wendreof.retrofit.service.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class RetrofitInitializer {

    /*val okHttp = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .build()
        */

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://logistica-ws.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun service() = retrofit.create(Service::class.java)
        }



