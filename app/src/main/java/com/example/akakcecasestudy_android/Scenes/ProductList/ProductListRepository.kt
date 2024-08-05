package com.example.akakcecasestudy_android.Scenes.ProductList

import com.example.akakcecasestudy_android.Model.Domain.Product
import com.example.akakcecasestudy_android.Model.Domain.Rating
import com.example.akakcecasestudy_android.Model.Networking.ProductResponse
import com.example.akakcecasestudy_android.Scenes.ProductFactory
import javax.inject.Inject

interface ProductListRepository {
    suspend fun getHorizontalProducts(): List<Product>
    suspend fun getProducts(): List<Product>
}

class ProductListRepositoryImp @Inject constructor(
    private val service: ProductListNetworkService,
    private val productFactory: ProductFactory
) : ProductListRepository {
    @Throws
    override suspend fun getHorizontalProducts(): List<Product> {
        return productFactory.makeFrom(service.getHorizontalProducts())
    }

    override suspend fun getProducts(): List<Product> {
        return productFactory.makeFrom(service.getProducts())
    }
}