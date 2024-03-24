package com.daisy.foodorder.di

import com.daisy.foodorder.data.repositories.ProductRepository
import com.daisy.foodorder.data.repositories.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {
    @Binds
    @ViewModelScoped
    fun bindProductRepository(
        impl: ProductRepositoryImpl,
    ): ProductRepository
}