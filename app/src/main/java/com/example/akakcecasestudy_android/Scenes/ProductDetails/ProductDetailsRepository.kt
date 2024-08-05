package com.example.akakcecasestudy_android.Scenes.ProductDetails

import com.example.akakcecasestudy_android.Model.Domain.Product
import com.example.akakcecasestudy_android.Model.Domain.Rating
import javax.inject.Inject

class ProductDetailsRepository @Inject constructor(
    private val service: ProductDetailsNetworkService
) {
    suspend fun getProductDetails(id: Int): Product {
        val network = service.getProductDetails(id)

        return Product(
            id = network.id,
            title = network.title,
            price = network.price,
            description = network.description,
            category = network.category,
            image = network.image,
            rating = Rating(network.rating.rate, network.rating.count)
        )
    }
}