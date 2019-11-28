package org.m0skit0.android.ikeachallenge.data.repository

import arrow.fx.IO
import com.google.gson.Gson
import org.koin.core.KoinComponent
import org.koin.core.get
import org.m0skit0.android.ikeachallenge.data.api.ProductsDto
import org.m0skit0.android.ikeachallenge.di.NAMED_MOCK_PRODUCTS_PROVIDER
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.domain.toProductsByType
import java.io.InputStream

internal class ProductRepositoryMock : ProductRepository, KoinComponent {
    override fun getProducts(): IO<List<Product>> = IO {
        get<() -> InputStream>(NAMED_MOCK_PRODUCTS_PROVIDER)().use { jsonInputStream ->
            jsonInputStream.reader().use { jsonReader ->
                get<Gson>().fromJson(jsonReader, ProductsDto::class.java).toProductsByType()
            }
        }
    }
}