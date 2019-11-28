package org.m0skit0.android.ikeachallenge.di

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.BuildConfig
import org.m0skit0.android.ikeachallenge.data.api.OptionTypeAdapterFactory
import org.m0skit0.android.ikeachallenge.data.api.ProductApi
import org.m0skit0.android.ikeachallenge.data.repository.ProductRepositoryImpl
import org.m0skit0.android.ikeachallenge.data.repository.ProductRepositoryMock
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.log.AndroidLogger
import org.m0skit0.android.ikeachallenge.log.Logger
import org.m0skit0.android.ikeachallenge.usecase.GetProductsUseCase
import org.m0skit0.android.ikeachallenge.usecase.GetProductsUseCaseImpl
import org.m0skit0.android.ikeachallenge.util.asAsset
import org.m0skit0.android.ikeachallenge.view.grid.ProductGridFragment
import org.m0skit0.android.ikeachallenge.view.grid.ProductListingViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream

private val utilsModule = module {
    single<Logger> { AndroidLogger() }
}

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

internal val NAMED_MOCK_PRODUCTS_PROVIDER = named("NAMED_MOCK_PRODUCTS_PROVIDER")

private val repositoryModule = module {

    val mockJsonAsset = "products.json"
    single<() -> InputStream>(NAMED_MOCK_PRODUCTS_PROVIDER) {
        { mockJsonAsset.asAsset() }
    }

    single<ProductRepository> {
        if (BuildConfig.MOCK_REPOSITORY) {
            ProductRepositoryMock()
        } else {
            ProductRepositoryImpl()
        }
    }
}

private val useCaseModule = module {
    single<GetProductsUseCase> { GetProductsUseCaseImpl() }
}

internal val NAMED_MUTABLE_LIST_PRODUCTS = named("NAMED_MUTABLE_LIST_PRODUCTS")
internal val NAMED_MUTABLE_BOOLEAN = named("NAMED_MUTABLE_BOOLEAN")
internal val NAMED_MUTABLE_ERROR = named("NAMED_MUTABLE_ERROR")

private val viewModelModule = module {
    factory(NAMED_MUTABLE_LIST_PRODUCTS) { MutableLiveData<List<Product>>() }
    factory(NAMED_MUTABLE_BOOLEAN) { MutableLiveData<Boolean>() }
    factory(NAMED_MUTABLE_ERROR) { MutableLiveData<Throwable>() }

    scope(named<ProductGridFragment>()) {
        viewModel { ProductListingViewModel() }
    }
}

private val modules = listOf(utilsModule, dataModule, repositoryModule, useCaseModule, viewModelModule)

internal fun Application.initializeKoin() {
    startKoin {
        androidContext(this@initializeKoin)
        modules(modules)
    }
}