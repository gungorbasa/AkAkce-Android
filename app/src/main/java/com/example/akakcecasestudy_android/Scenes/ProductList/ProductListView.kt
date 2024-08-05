package com.example.akakcecasestudy_android.Scenes.ProductList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.akakcecasestudy_android.Scenes.ProductDetails.ProductDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
object ProductListScreen

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProductListView(
    viewModel: ProductListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val viewState by viewModel.viewState

    LaunchedEffect(Unit) {
        viewModel.onLaunch()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Product List") }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            viewState?.let { state ->
                if (state.errorMessage != null) {
                    Text(state.errorMessage)
                } else {
                    ProductListHorizontalPagerView(
                        products = state.horizontalProducts,
                        onSelectItem = { id ->
                            navController.navigate(ProductDetailsScreen(id = id))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    GridListView(
                        products = state.products,
                        onSelectItem = { id ->
                            navController.navigate(ProductDetailsScreen(id = id))
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } ?: run {
                Text("Loading...")
            }
        }
    }
}