package com.wendreof.retrofit.service

import com.wendreof.model.Product
import com.wendreof.retrofit.RetrofitInitializer
import com.wendreof.retrofit.callback.callback

class WebClient {

    fun list(
        success: (products: List<Product>) -> Unit,
        failure: (throwable: Throwable) -> Unit
    )
    {
        val call = RetrofitInitializer().service().list()
        call.enqueue(callback(
            { response ->
            response?.body()?.let {
                success(it)
            }
        },
            { throwable ->
            throwable?.let {
                failure(it)
            }
        }))
    }

    fun insert(
        product: Product, finished: () -> Unit, success: (product: Product) -> Unit,
        failure: (throwable: Throwable) -> Unit
    )
    {
        val call = RetrofitInitializer().service().insert(product)
        call.enqueue(callback({ response ->
            response?.body()?.let {
                success(it)
                finished()
            }
        },
            { throwable ->
            throwable?.let {
                failure(it)
                finished()
            }
        }))
    }

    fun alter(product: Product, success: (product: Product) -> Unit,
              failure: (throwable: Throwable) -> Unit) {
        val call = RetrofitInitializer().service().alter(product, product.id)
        call.enqueue(callback({ response ->
            response?.body()?.let {
                success(it)
            }
        },
            { throwable ->
            throwable?.let {
                failure(it)
            }
        }))
    }
}