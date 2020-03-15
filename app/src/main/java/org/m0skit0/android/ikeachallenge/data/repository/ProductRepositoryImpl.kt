package org.m0skit0.android.ikeachallenge.data.repository

import arrow.fx.IO
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.data.api.ProductApi
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.domain.toProductsByType
import org.m0skit0.android.ikeachallenge.error.APIException

internal class ProductRepositoryImpl : ProductRepository, KoinComponent {

    private val productApi: ProductApi by inject()

    override fun getProducts(): IO<List<Product>> = IO {
        productApi.getProducts().execute().run {
            if (isSuccessful) {
                body()?.toProductsByType() ?: throw APIException("Body is null")
            } else {
                throw APIException(errorBody()?.string() ?: "Error body is null")
            }
        }
    }
}
