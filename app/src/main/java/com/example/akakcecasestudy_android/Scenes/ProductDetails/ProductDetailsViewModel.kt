package com.example.akakcecasestudy_android.Scenes.ProductDetails

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akakcecasestudy_android.Model.Domain.Product
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface ProductDetailsViewModel {
    data class State(
        val product: Product
    )

    @AssistedFactory
    interface Factory {
        fun create(productId: Int): ProductDetailsViewModelImp
    }

    suspend fun onLaunch()
}

@HiltViewModel(assistedFactory = ProductDetailsViewModel.Factory::class)
class ProductDetailsViewModelImp @AssistedInject constructor(
    @Assisted private val productId: Int,
    private val repository: ProductDetailsRepository
) : ViewModel(), ProductDetailsViewModel {


    private val _viewState = mutableStateOf<ProductDetailsViewModel.State?>(null)
    val viewState: androidx.compose.runtime.State<ProductDetailsViewModel.State?> = _viewState

    override suspend fun onLaunch() {
        viewModelScope.async {
            try {
                val product = repository.getProductDetails(productId)
                _viewState.value = ProductDetailsViewModel.State(product)
            } catch (e: Exception) {
                Log.e("ProductListViewModel", "Error loading data", e)
            }
        }
    }
}