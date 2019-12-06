package org.m0skit0.android.ikeachallenge.usecase

import arrow.core.Either
import org.m0skit0.android.ikeachallenge.domain.Product

internal interface GetProductUseCase {
    suspend operator fun invoke(productId: String): Either<Throwable, Product>
}