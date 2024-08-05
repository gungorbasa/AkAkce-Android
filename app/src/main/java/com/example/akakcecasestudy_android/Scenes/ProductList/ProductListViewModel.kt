package com.example.akakcecasestudy_android.Scenes.ProductList

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akakcecasestudy_android.Model.Domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductListState(
    val horizontalProducts: List<Product>,
    val products: List<Product>,
    val errorMessage: String? = null
)

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductListRepository
) : ViewModel() {
    private val _viewState = mutableStateOf<ProductListState?>(null)
    val viewState: State<ProductListState?> = _viewState

    fun onLaunch() {
        viewModelScope.async {
            try {
                val horizontalProductsDeferred = async { repository.getHorizontalProducts() }
                val productsDeferred = async { repository.getProducts() }

                val horizontalProducts = horizontalProductsDeferred.await()
                val products = productsDeferred.await()

                _viewState.value = ProductListState(
                    horizontalProducts = horizontalProducts,
                    products = products
                )
            } catch (e: Exception) {
                Log.e("ProductListViewModel", "Error loading data", e)
                _viewState.value = ProductListState(
                    horizontalProducts = emptyList(),
                    products = emptyList(),
                    errorMessage = "We have encountered a problem!"
                )
            }
        }
    }
}