package com.example.akakcecasestudy_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.akakcecasestudy_android.Scenes.ProductDetails.ProductDetailsScreen
import com.example.akakcecasestudy_android.Scenes.ProductDetails.ProductDetailsView
import com.example.akakcecasestudy_android.Scenes.ProductList.ProductListScreen
import com.example.akakcecasestudy_android.Scenes.ProductList.ProductListView
import com.example.akakcecasestudy_android.ui.theme.AkakceCaseStudyAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AkakceCaseStudyAndroidTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ProductListScreen
                ) {
                    composable<ProductListScreen> { ProductListView(navController = navController) }
                    composable<ProductDetailsScreen> { entry ->
                        val productId = entry.toRoute<ProductDetailsScreen>().id

                        ProductDetailsView(id = productId, navController = navController)
                    }
                }
            }
        }
    }
}