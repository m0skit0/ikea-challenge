package org.m0skit0.android.ikeachallenge.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import org.koin.core.module.Module
import org.m0skit0.android.ikeachallenge.BuildConfig
import org.m0skit0.android.ikeachallenge.data.api.OptionTypeAdapterFactory
import org.m0skit0.android.ikeachallenge.data.api.ProductApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module

internal object DataModule : KoinModuleProvider {
    override fun module(): Module  = module {
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
}
