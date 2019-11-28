package org.m0skit0.android.ikeachallenge.data.repository

import arrow.fx.IO
import com.google.gson.Gson
import org.koin.core.KoinComponent
import org.koin.core.get
import org.m0skit0.android.ikeachallenge.data.api.ProductsDto
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.domain.toProductsByType
import org.m0skit0.android.ikeachallenge.util.asAsset

private const val JSON_ASSET = "products.json"

internal class ProductRepositoryMock : ProductRepository, KoinComponent {
    override fun getProducts(): IO<List<Product>> = IO {
        JSON_ASSET.asAsset().use { jsonInputStream ->
            jsonInputStream.reader().use { jsonReader ->
                get<Gson>().fromJson(jsonReader, ProductsDto::class.java).toProductsByType()
            }
        }
    }
}