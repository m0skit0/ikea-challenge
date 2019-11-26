package org.m0skit0.android.ikeachallenge.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.BuildConfig
import org.m0skit0.android.ikeachallenge.data.api.CouchInfo
import org.m0skit0.android.ikeachallenge.data.api.Info
import org.m0skit0.android.ikeachallenge.data.api.OptionTypeAdapterFactory
import org.m0skit0.android.ikeachallenge.data.api.ProductApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val NAMED_OPTION_FACTORY = named("NAMED_OPTION_FACTORY")
private val NAMED_INFO_FACTORY = named("NAMED_INFO_FACTORY")

private val dataModule = module {
    single<TypeAdapterFactory>(NAMED_OPTION_FACTORY) { OptionTypeAdapterFactory() }
    single<TypeAdapterFactory>(NAMED_INFO_FACTORY) {
        RuntimeTypeAdapterFactory.of(Info::class.java, "type")
            .registerSubtype(CouchInfo::class.java, "couch")

    }
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

private val modules = listOf(dataModule)

internal fun Application.initializeKoin() {
    startKoin {
        androidContext(this@initializeKoin)
        modules(modules)
    }
}