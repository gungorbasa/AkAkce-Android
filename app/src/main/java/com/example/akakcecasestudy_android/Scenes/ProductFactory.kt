package com.example.akakcecasestudy_android.Scenes

import com.example.akakcecasestudy_android.Model.Domain.Product
import com.example.akakcecasestudy_android.Model.Domain.Rating
import com.example.akakcecasestudy_android.Model.Networking.ProductResponse
import javax.inject.Inject

interface ProductFactory {
    fun makeFrom(network: ProductResponse): Product {
        return Product(
            network.id,
            network.title,
            network.price,
            network.description,
            network.category,
            network.image,
            Rating(network.rating.rate, network.rating.count)
        )
    }

    fun makeFrom(network: List<ProductResponse>): List<Product> {
        return network.map { makeFrom(it) }
    }
}

class DefaultProductFactory @Inject constructor() : ProductFactory {}