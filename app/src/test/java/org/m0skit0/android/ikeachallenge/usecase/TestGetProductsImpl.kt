package org.m0skit0.android.ikeachallenge.usecase

import arrow.fx.IO
import io.kotlintest.TestCase
import io.kotlintest.assertions.arrow.either.shouldBeLeftOfType
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.KoinFreeSpec
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import java.io.IOException

class TestGetProductsImpl : KoinFreeSpec() {

    @MockK
    private lateinit var mockProductRepository: ProductRepository

    override val module = module {
        single { mockProductRepository }
    }

    override fun beforeTest(testCase: TestCase) {
        MockKAnnotations.init(this)
        super.beforeTest(testCase)
    }

    init {
        "when product repository throws exception use case must return error" {
            every { mockProductRepository.getProducts() } returns IO { throw IOException() }
            runBlocking { GetProductsImpl()() }.shouldBeLeftOfType<IOException>()
        }

        "when product repository returns data use case must return data" {
            every { mockProductRepository.getProducts() } returns IO { emptyList<Product>() }
            runBlocking { GetProductsImpl()() } shouldBeRight emptyList()
        }
    }
}