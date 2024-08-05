package com.example.akakcecasestudy_android.Networking

import com.example.akakcecasestudy_android.Model.Networking.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsEndpoint {
    @GET("products/{productId}")
    suspend fun getProductById(
        @Path("productId") productId: Int
    ): Response<ProductResponse>
}