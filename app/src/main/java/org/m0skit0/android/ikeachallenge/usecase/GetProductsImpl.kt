package org.m0skit0.android.ikeachallenge.usecase

import arrow.core.Either
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository

internal class GetProductsImpl : GetProducts, KoinComponent {

    private val productRepository: ProductRepository by inject()

    override suspend fun invoke(): Either<Throwable, List<Product>> =
        productRepository.getProducts().attempt().unsafeRunSync()
}