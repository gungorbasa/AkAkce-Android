package com.example.akakcecasestudy_android.Scenes.ProductList

import com.example.akakcecasestudy_android.Model.Networking.ProductResponse
import com.example.akakcecasestudy_android.Networking.ProductListEndpoint
import javax.inject.Inject

interface ProductListNetworkService {
    suspend fun getHorizontalProducts(): List<ProductResponse>
    suspend fun getProducts(): List<ProductResponse>
}

class ProductListNetworkServiceImp @Inject constructor(
    private val productListEndpoint: ProductListEndpoint
) : ProductListNetworkService {
    override suspend fun getHorizontalProducts(): List<ProductResponse> {
        val response = productListEndpoint.getHorizontalProducts()

        return when {
            response.isSuccessful -> response.body()
                ?: throw IllegalStateException("Response body is null")

            else -> throw RuntimeException("Error: ${response.code()} - ${response.message()}")
        }
    }

    override suspend fun getProducts(): List<ProductResponse> {
        val response = productListEndpoint.getProducts()

        return when {
            response.isSuccessful -> response.body()
                ?: throw IllegalStateException("Response body is null")

            else -> throw RuntimeException("Error: ${response.code()} - ${response.message()}")
        }
    }
}