package com.wendreof.retrofit

interface CallbackResponse<T>
{
    fun success(response: T)
}