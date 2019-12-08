package org.m0skit0.android.ikeachallenge.usecase

import arrow.core.toOption
import arrow.fx.IO
import io.kotlintest.TestCase
import io.kotlintest.assertions.arrow.either.shouldBeLeftOfType
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.KoinFreeSpec
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductRepository
import org.m0skit0.android.ikeachallenge.error.ProductNotFound
import java.io.IOException

class TestGetProductUseCaseImpl : KoinFreeSpec() {

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
            runBlocking { GetProductUseCaseImpl()("") }.shouldBeLeftOfType<IOException>()
        }

        "when id not found use case must return error" {
            every { mockProductRepository.getProducts() } returns IO { emptyList<Product>() }
            runBlocking { GetProductUseCaseImpl()("1") }.shouldBeLeftOfType<ProductNotFound>()
        }

        "when id matches a product id use case must return product" {
            val mockProduct: Product = mockk()
            every { mockProductRepository.getProducts() } returns IO { listOf(mockProduct) }
            every { mockProduct.id } returns "1".toOption()
            runBlocking { GetProductUseCaseImpl()("1") } shouldBeRight mockProduct
        }
    }
}