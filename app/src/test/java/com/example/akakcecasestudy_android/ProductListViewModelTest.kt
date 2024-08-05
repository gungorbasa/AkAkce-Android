package com.example.akakcecasestudy_android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.akakcecasestudy_android.Model.Domain.Product
import com.example.akakcecasestudy_android.Model.Domain.Rating
import com.example.akakcecasestudy_android.Model.Networking.ProductResponse
import com.example.akakcecasestudy_android.Model.Networking.RatingResponse
import com.example.akakcecasestudy_android.Scenes.ProductList.ProductListRepository
import com.example.akakcecasestudy_android.Scenes.ProductList.ProductListRepositoryImp
import com.example.akakcecasestudy_android.Scenes.ProductList.ProductListState
import com.example.akakcecasestudy_android.Scenes.ProductList.ProductListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ProductListViewModelTest {
    private val products = listOf(
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

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: ProductListRepository

    private lateinit var viewModel: ProductListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = ProductListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onLaunch should fetch products and update viewState`() = runTest {
        val horizontalProducts = listOf(products.first())
        val products = products

        whenever(repository.getHorizontalProducts()).thenReturn(horizontalProducts)
        whenever(repository.getProducts()).thenReturn(products)

        viewModel.onLaunch()

        val state = viewModel.viewState.value
        assertNotNull(state)
        assertEquals(horizontalProducts, state!!.horizontalProducts)
        assertEquals(products, state.products)
    }

    @Test
    fun `onLaunch should handle errors and update viewState with errorMessage`() = runTest {
        // Arrange
        whenever(repository.getHorizontalProducts()).thenThrow(RuntimeException("Error"))
        whenever(repository.getProducts()).thenThrow(RuntimeException("Error"))

        // Act
        viewModel.onLaunch()

        // Assert
        val state = viewModel.viewState.value
        assertNotNull(state)
        assertEquals(emptyList<Product>(), state!!.horizontalProducts)
        assertEquals(emptyList<Product>(), state.products)
//        assertEquals("We have encountered a problem!", state.errorMessage)
    }
}