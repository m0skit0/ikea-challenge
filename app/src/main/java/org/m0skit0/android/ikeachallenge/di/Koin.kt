package org.m0skit0.android.ikeachallenge.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.BuildConfig
import org.m0skit0.android.ikeachallenge.data.api.OptionTypeAdapterFactory
import org.m0skit0.android.ikeachallenge.data.api.ProductApi
import org.m0skit0.android.ikeachallenge.data.repository.ProductRepositoryImpl
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.usecase.GetProductsUseCase
import org.m0skit0.android.ikeachallenge.usecase.GetProductsUseCaseImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val dataModule = module {
    single<TypeAdapterFactory> { OptionTypeAdapterFactory() }
    single<Gson> {
        GsonBuilder()
            .registerTypeAdapterFactory(get())
            .create()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get()))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }
    single<ProductApi> { get<Retrofit>().create(ProductApi::class.java) }
}

private val repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImpl() }
}

private val useCaseModule = module {
    single<GetProductsUseCase> { GetProductsUseCaseImpl() }
}

private val modules = listOf(dataModule, repositoryModule, useCaseModule)

internal fun Application.initializeKoin() {
    startKoin {
        androidContext(this@initializeKoin)
        modules(modules)
    }
}