package com.wendreof.retrofit

import com.wendreof.retrofit.service.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://logistica-ws.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun service() = retrofit.create(Service::class.java)
        }



