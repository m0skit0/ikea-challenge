package org.m0skit0.android.ikeachallenge.data.api

import retrofit2.Call
import retrofit2.http.GET

internal interface ProductApi {
    @GET("/8yzvq")
    fun getProducts(): Call<ProductsDto>
}