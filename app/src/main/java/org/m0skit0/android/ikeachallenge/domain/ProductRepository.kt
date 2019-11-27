package org.m0skit0.android.ikeachallenge.domain

import arrow.fx.IO
import org.m0skit0.android.ikeachallenge.data.api.ProductsDto

internal interface ProductRepository {
    fun getProducts(): IO<ProductsDto>
}