package com.example.akakcecasestudy_android.DependencyInjection

import android.content.Context
import com.example.akakcecasestudy_android.Networking.NetworkMonitoring.LiveNetworkMonitor
import com.example.akakcecasestudy_android.Networking.NetworkMonitoring.NetworkMonitor
import com.example.akakcecasestudy_android.Networking.NetworkMonitoring.NetworkMonitorInterceptor
import com.example.akakcecasestudy_android.Networking.ProductDetailsEndpoint
import com.example.akakcecasestudy_android.Networking.ProductListEndpoint
import com.example.akakcecasestudy_android.Scenes.DefaultProductFactory
import com.example.akakcecasestudy_android.Scenes.ProductDetails.ProductDetailsNetworkService
import com.example.akakcecasestudy_android.Scenes.ProductDetails.ProductDetailsNetworkServiceImp
import com.example.akakcecasestudy_android.Scenes.ProductFactory
import com.example.akakcecasestudy_android.Scenes.ProductList.ProductListNetworkService
import com.example.akakcecasestudy_android.Scenes.ProductList.ProductListNetworkServiceImp
import com.example.akakcecasestudy_android.Scenes.ProductList.ProductListRepository
import com.example.akakcecasestudy_android.Scenes.ProductList.ProductListRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkInjection {
    @Provides
    fun provideBaseUrl(): String = "https://fakestoreapi.com/"

    @Provides
    fun provideNetworkMonitor(
        @ApplicationContext appContext: Context
    ): NetworkMonitor {
        return LiveNetworkMonitor(appContext)
    }

    @Provides
    @Singleton
    fun okHTTP(liveNetworkMonitor: NetworkMonitor): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val networkMonitorInterceptor = NetworkMonitorInterceptor(liveNetworkMonitor)

        return OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .addInterceptor(networkMonitorInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHTTP: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHTTP)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

@Module
@InstallIn(SingletonComponent::class)
class EndpointInjection {
    @Provides
    @Singleton
    fun provideProductListEndpoint(retrofit: Retrofit): ProductListEndpoint = retrofit.create(
        ProductListEndpoint::class.java
    )

    @Provides
    @Singleton
    fun provideProductDetailsEndpoint(retrofit: Retrofit): ProductDetailsEndpoint = retrofit.create(
        ProductDetailsEndpoint::class.java
    )
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkServiceInjection {
    @Provides
    @Singleton
    fun provideProductDetailsNetworkService(endpoint: ProductDetailsEndpoint): ProductDetailsNetworkService {
        return ProductDetailsNetworkServiceImp(endpoint)
    }

    @Provides
    @Singleton
    fun provideProductListNetworkService(endpoint: ProductListEndpoint): ProductListNetworkService {
        return ProductListNetworkServiceImp(endpoint)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryInjection {
    @Provides
    @Singleton
    fun provideProductListRepository(
        service: ProductListNetworkService,
        factory: ProductFactory
    ): ProductListRepository {
        return ProductListRepositoryImp(service, factory)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class FactoryInjection {
    @Provides
    @Singleton
    fun provideProductFactory(): ProductFactory {
        return DefaultProductFactory()
    }
}
