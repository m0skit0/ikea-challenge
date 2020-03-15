package org.m0skit0.android.ikeachallenge.di

import org.koin.core.module.Module
import org.m0skit0.android.ikeachallenge.usecase.GetProduct
import org.m0skit0.android.ikeachallenge.usecase.GetProductImpl
import org.m0skit0.android.ikeachallenge.usecase.GetProducts
import org.m0skit0.android.ikeachallenge.usecase.GetProductsImpl
import org.koin.dsl.module

internal object UseCaseModule : KoinModuleProvider {
    override fun module(): Module  = module {
        single<GetProducts> { GetProductsImpl() }
        single<GetProduct> { GetProductImpl() }
    }
}
