package com.example.akakcecasestudy_android.Scenes.ProductDetails

import com.example.akakcecasestudy_android.Model.Networking.ProductResponse
import com.example.akakcecasestudy_android.Networking.ProductDetailsEndpoint
import javax.inject.Inject

interface ProductDetailsNetworkService {
    suspend fun getProductDetails(id: Int): ProductResponse
}

class ProductDetailsNetworkServiceImp @Inject constructor(
    private val detailsEndpoint: ProductDetailsEndpoint
) : ProductDetailsNetworkService {
    override suspend fun getProductDetails(id: Int): ProductResponse {
        val response = detailsEndpoint.getProductById(id)

        return when {
            response.isSuccessful -> response.body()
                ?: throw IllegalStateException("Response body is null")

            else -> throw RuntimeException("Error: ${response.code()} - ${response.message()}")
        }
    }
}