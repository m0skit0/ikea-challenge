package org.m0skit0.android.ikeachallenge.di

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.m0skit0.android.ikeachallenge.BuildConfig
import org.m0skit0.android.ikeachallenge.data.repository.ProductRepositoryImpl
import org.m0skit0.android.ikeachallenge.data.repository.mock.ProductRepositoryMock
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.util.asAsset
import java.io.InputStream

internal object RepositoryModule : KoinModuleProvider {

    val NAMED_MOCK_PRODUCTS_PROVIDER = named("NAMED_MOCK_PRODUCTS_PROVIDER")

    override fun module(): Module = org.koin.dsl.module {

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
}
