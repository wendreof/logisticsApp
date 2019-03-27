package com.wendreof.retrofit

import com.wendreof.model.Product

interface ListResponse {
    fun success(products: List<Product>)
}