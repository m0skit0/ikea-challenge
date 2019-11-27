package org.m0skit0.android.ikeachallenge.data.repository

import arrow.fx.IO
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository

internal class ProductRepositoryMock : ProductRepository {
    override fun getProducts(): IO<List<Product>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}