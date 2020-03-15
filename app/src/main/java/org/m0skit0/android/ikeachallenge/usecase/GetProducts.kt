package org.m0skit0.android.ikeachallenge.usecase

import arrow.core.Either
import org.m0skit0.android.ikeachallenge.domain.Product

internal interface GetProducts {
    suspend operator fun invoke(): Either<Throwable, List<Product>>
}