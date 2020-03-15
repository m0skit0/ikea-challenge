package org.m0skit0.android.ikeachallenge.data.repository.mock

import arrow.fx.IO
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.get
import org.m0skit0.android.ikeachallenge.data.api.ProductsDto
import org.m0skit0.android.ikeachallenge.di.RepositoryModule.NAMED_MOCK_PRODUCTS_PROVIDER
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.domain.toProductsByType
import java.io.InputStream

/**
 * Mock for product repository so we can test without an actual remote server.
 */
internal class ProductRepositoryMock : ProductRepository, KoinComponent {

    /**
     * Uses local asset to load product information.
     * Note that images are still URLs.
     */
    override fun getProducts(): IO<List<Product>> = IO {
        runBlocking { delay(2000) } // Simulate a delay
        get<() -> InputStream>(NAMED_MOCK_PRODUCTS_PROVIDER)().use { jsonInputStream ->
            jsonInputStream.reader().use { jsonReader ->
                get<Gson>().fromJson(jsonReader, ProductsDto::class.java).toProductsByType()
            }
        }
    }

    /**
     * Uses in-memory cart.
     */
    override fun addProductToCart(id: String): IO<Unit> = IO {
        MockCart.addItem(id)
    }
}
