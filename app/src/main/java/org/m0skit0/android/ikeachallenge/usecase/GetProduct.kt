package org.m0skit0.android.ikeachallenge.usecase

import arrow.core.Either
import arrow.core.extensions.list.functorFilter.filter
import arrow.core.flatMap
import arrow.core.toOption
import org.m0skit0.android.ikeachallenge.di.koin
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.error.ProductNotFound

private fun productRepository(): ProductRepository = koin().get()

internal fun product(productId: String): Either<Throwable, Product> =
    productRepository()
        .getProducts()
        .attempt()
        .unsafeRunSync()
        .flatMap { products ->
            products
                .filter { it.id.isDefined() }
                .firstOrNull { it.id.orNull() == productId }
                .toOption()
                .toEither { ProductNotFound("No product found with id $productId") }
        }

internal fun products(): Either<Throwable, List<Product>> =
    productRepository().getProducts().attempt().unsafeRunSync()
