package com.wendreof.retrofit.service

import android.util.Log
import com.wendreof.model.Product
import com.wendreof.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.wendreof.retrofit.*

class WebClient {

    fun list(listResponse: CallbackResponse<List<Product>>) {
        val call = RetrofitInitializer().service().list()
        call.enqueue(object : Callback<List<Product>?> {
            override fun onResponse(call: Call<List<Product>?>?,
                                    response: Response<List<Product>?>?){
                response?.body()?.let {
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
        fun insert(product: Product, listResponse: CallbackResponse<Product>){
            val call = RetrofitInitializer().service().insert(product)
            call.enqueue(object: Callback<Product?> {
                override fun onResponse(call: Call<Product?>?, response: Response<Product?>?) {
                    response?.body()?.let{
                        val product: Product = it
                        listResponse.success(product)
                    }
                }

                override fun onFailure(call: Call<Product?>?, t: Throwable?) {

                }
            })
        }
    }
