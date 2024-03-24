package com.daisy.foodorder.di

import com.daisy.foodorder.data.network.services.ProductCategoryService
import com.daisy.foodorder.data.network.services.ProductService
import com.daisy.foodorder.data.repositories.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryImpModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        productService: ProductService,
        categoryService: ProductCategoryService,
        ioDispatcher: CoroutineDispatcher,
    ): ProductRepositoryImpl = ProductRepositoryImpl(productService, categoryService, ioDispatcher)

    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}