package org.m0skit0.android.ikeachallenge.data.repository

import arrow.fx.IO
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.data.api.ProductApi
import org.m0skit0.android.ikeachallenge.data.api.ProductsDto
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.error.APIException

internal class ProductRepositoryImpl : ProductRepository, KoinComponent {

    private val productApi: ProductApi by inject()

    override fun getProducts(): IO<ProductsDto> = IO {
        productApi.getProducts().execute().run {
            if (isSuccessful) {
                body() ?: throw APIException("Body is null")
            } else {
                throw APIException(errorBody()?.string() ?: "Error body is null")
            }
        }
    }
}