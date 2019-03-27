package com.wendreof.retrofit

import com.wendreof.model.Product

interface CallbackResponse<T> {
    fun success(response: T)
}