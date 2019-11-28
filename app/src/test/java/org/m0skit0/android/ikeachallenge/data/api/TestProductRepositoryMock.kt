package org.m0skit0.android.ikeachallenge.data.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.matchers.collections.shouldNotBeEmpty
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.m0skit0.android.ikeachallenge.data.repository.ProductRepositoryMock
import org.m0skit0.android.ikeachallenge.di.NAMED_MOCK_PRODUCTS_PROVIDER
import org.m0skit0.android.ikeachallenge.shouldNotBeCalled
import java.io.InputStream

class TestProductRepositoryMock : FreeSpec(), KoinTest {

    private val module = module {
        single<() -> InputStream>(NAMED_MOCK_PRODUCTS_PROVIDER) {
            { javaClass.getResourceAsStream("/products-mock.json")!! }
        }
        single<TypeAdapterFactory> { OptionTypeAdapterFactory() }
        single<Gson> {
            GsonBuilder()
                .registerTypeAdapterFactory(get())
                .create()
        }
    }

    override fun beforeTest(testCase: TestCase) {
        startKoin {
            modules(module)
        }
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        stopKoin()
    }

    init {
        "when mock reads json should return product list" {
            ProductRepositoryMock().getProducts().attempt().unsafeRunSync().fold({ shouldNotBeCalled() }) {
                with (it) {
                    size shouldBe 14
                }
            }
        }
    }
}