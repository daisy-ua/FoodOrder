package com.daisy.foodorder.di

import com.daisy.foodorder.BuildConfig
import com.daisy.foodorder.data.network.services.ProductCategoryService
import com.daisy.foodorder.data.network.services.ProductCategoryServiceImpl
import com.daisy.foodorder.data.network.services.ProductService
import com.daisy.foodorder.data.network.services.ProductServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://my.api.mockaroo.com"

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(DefaultRequest) {
                url(BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("X-Api-Key", BuildConfig.API_KEY)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Singleton
    @Provides
    fun provideProductService(httpClient: HttpClient): ProductService =
        ProductServiceImpl(httpClient)

    @Singleton
    @Provides
    fun provideProductCategoryService(httpClient: HttpClient): ProductCategoryService =
        ProductCategoryServiceImpl(httpClient)
}