package org.m0skit0.android.ikeachallenge.data.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import io.kotlintest.assertions.arrow.either.shouldBeLeftOfType
import io.kotlintest.shouldBe
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.KoinFreeSpec
import org.m0skit0.android.ikeachallenge.data.repository.mock.ProductRepositoryMock
import org.m0skit0.android.ikeachallenge.di.NAMED_MOCK_PRODUCTS_PROVIDER
import org.m0skit0.android.ikeachallenge.shouldNotBeCalled
import java.io.IOException
import java.io.InputStream

class TestProductRepositoryMock : KoinFreeSpec() {

    override val module = module {
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

    init {
        "when mock reads json should return product list" {
            ProductRepositoryMock().getProducts().attempt().unsafeRunSync().fold({ shouldNotBeCalled() }) {
                with (it) {
                    size shouldBe 14
                }
            }
        }

        "when mock error should return error" {
            loadKoinModules(
                module {
                    single<() -> InputStream>(NAMED_MOCK_PRODUCTS_PROVIDER, override = true) {
                        { throw IOException() }
                    }
                }
            )
            ProductRepositoryMock().getProducts().attempt().unsafeRunSync().shouldBeLeftOfType<IOException>()
        }
    }
}