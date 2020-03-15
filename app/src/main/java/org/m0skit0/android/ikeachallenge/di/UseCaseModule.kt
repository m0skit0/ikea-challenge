package org.m0skit0.android.ikeachallenge.di

import arrow.core.Either
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.usecase.*

internal object UseCaseModule : KoinModuleProvider {

    val NAMED_GET_PRODUCT = named("NAMED_GET_PRODUCT")
    val NAMED_GET_PRODUCTS = named("NAMED_GET_PRODUCTS")

    override fun module(): Module  = module {
        single<(String) -> Either<Throwable, Product>>(NAMED_GET_PRODUCT) { ::product }
        single<() -> Either<Throwable, List<Product>>>(NAMED_GET_PRODUCTS) { ::products }
    }
}
