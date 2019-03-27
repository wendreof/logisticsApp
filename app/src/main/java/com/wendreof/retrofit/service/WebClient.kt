package com.wendreof.retrofit.service

import android.util.Log
import com.wendreof.model.Product
import com.wendreof.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.wendreof.retrofit.*

class WebClient {

    fun list(listResponse: ListResponse) {
        val call = RetrofitInitializer().service().list()
        call.enqueue(object: Callback<List<Product>?> {
            override fun onResponse(call: Call<List<Product>?>?,
                                    response: Response<List<Product>?>?) {
                response?.body()?.let{
                    val products: List<Product> = it
                    listResponse.success(products)
                }
            }

            override fun onFailure(call: Call<List<Product>?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                //Toast.makeText(applicationContext,"onFailure error", message, Toast.LENGTH_LONG).show
            }
        })
    }
}