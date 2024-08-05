package com.example.akakcecasestudy_android.Scenes.ProductDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable


@Serializable
data class ProductDetailsScreen(
    val id: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsView(
    navController: NavController, id: Int, modifier: Modifier = Modifier
) {
    val viewModel: ProductDetailsViewModelImp =
        hiltViewModel(creationCallback = { factory: ProductDetailsViewModel.Factory ->
            factory.create(id)
        })

    LaunchedEffect(Unit) {
        viewModel.onLaunch()
    }

    val viewState by viewModel.viewState

    NavigationBarView(
        viewState = viewState,
        navController = navController,
        title = "Product Details",
        modifier = modifier.padding(16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationBarView(
    viewState: ProductDetailsViewModel.State?,
    navController: NavController,
    title: String,
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {
        TopAppBar(colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = { Text(title) }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        })
    }) { innerPadding ->
        viewState?.let { state ->
            DetailsView(
                state = state, modifier = modifier.padding(innerPadding)
            )
        } ?: run {
            LoadingView(modifier = modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun DetailsView(state: ProductDetailsViewModel.State, modifier: Modifier) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = state.product.title, style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.size(16.dp))
        AsyncImage(
            model = state.product.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))

        RatingBarView(
            rating = state.product.rating.rate,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = state.product.description,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}

@Composable
private fun LoadingView(modifier: Modifier) {
    Text("Loading...", modifier = modifier)
}