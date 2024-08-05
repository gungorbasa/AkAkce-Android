package com.example.akakcecasestudy_android.Networking

import com.example.akakcecasestudy_android.Model.Networking.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductListEndpoint {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductResponse>>

    @GET("products")
    suspend fun getHorizontalProducts(
        @Query("limit") limit: Int? = 5
    ): Response<List<ProductResponse>>
}
