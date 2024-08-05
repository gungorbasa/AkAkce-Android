package com.example.akakcecasestudy_android

import com.example.akakcecasestudy_android.Model.Domain.Product
import com.example.akakcecasestudy_android.Model.Domain.Rating
import com.example.akakcecasestudy_android.Model.Networking.ProductResponse
import com.example.akakcecasestudy_android.Model.Networking.RatingResponse
import com.example.akakcecasestudy_android.Scenes.DefaultProductFactory
import org.junit.Test

import org.junit.Assert.*

/**
 * DefaultProductFactoryTest local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DefaultProductFactoryTest {
    private val factory = DefaultProductFactory()
    private val networkProducts = listOf(
        ProductResponse(
            id = 1,
            title = "Product 1",
            price = 15.0,
            description = "Description 1",
            category = "Category 1",
            image = "image1.png",
            rating = RatingResponse(rate = 4.0, count = 50)
        ),
        ProductResponse(
            id = 2,
            title = "Product 2",
            price = 20.0,
            description = "Description 2",
            category = "Category 2",
            image = "image2.png",
            rating = RatingResponse(rate = 4.5, count = 75)
        )
    )

    private val expectedProducts = listOf(
        Product(
            id = 1,
            title = "Product 1",
            price = 15.0,
            description = "Description 1",
            category = "Category 1",
            image = "image1.png",
            rating = Rating(rate = 4.0, count = 50)
        ),
        Product(
            id = 2,
            title = "Product 2",
            price = 20.0,
            description = "Description 2",
            category = "Category 2",
            image = "image2.png",
            rating = Rating(rate = 4.5, count = 75)
        )
    )

    @Test
    fun make_from_single_product_response() {
        val networkProduct = networkProducts.first()
        val expectedProduct = expectedProducts.first()

        val actualProduct = factory.makeFrom(networkProduct)

        assertEquals(expectedProduct, actualProduct)
    }

    @Test
    fun make_from_list_of_product_responses() {
        val actualProducts = factory.makeFrom(networkProducts)

        assertEquals(expectedProducts, actualProducts)
    }
}