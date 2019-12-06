package org.m0skit0.android.ikeachallenge.usecase

import arrow.core.Either
import arrow.core.extensions.list.foldable.firstOption
import arrow.core.extensions.list.functorFilter.filter
import arrow.core.flatMap
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.error.ProductNotFoundException

internal class GetProductUseCaseImpl : GetProductUseCase, KoinComponent {

    private val productRepository: ProductRepository by inject()

    override suspend fun invoke(productId: String): Either<Throwable, Product> =
        productRepository
            .getProducts()
            .attempt()
            .unsafeRunSync()
            .flatMap { products ->
                products
                    .filter { it.id.isDefined() }
                    .firstOption { it.id.orNull() == productId }
                    .toEither { ProductNotFoundException("No product found with id $productId") }
            }
}