package org.m0skit0.android.ikeachallenge.data.api

import arrow.core.Option
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import io.kotlintest.assertions.arrow.option.shouldBeNone
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.m0skit0.android.ikeachallenge.shouldNotBeCalled

class TestProductsDto : AutoCloseKoinTest() {

    private val module = module {
        single<TypeAdapterFactory> { OptionTypeAdapterFactory() }
        single<Gson> {
            GsonBuilder()
                .registerTypeAdapterFactory(get())
                .create()
        }
    }

    @Before
    fun setup() {
        startKoin {
            modules(module)
        }
    }

    @Test
    fun `when parsing correct product JSON should return correct DTO`() {
        "/products.json".resourceToProductsDto { productsDto ->
            productsDto.products.fold({ shouldNotBeCalled() }) { products ->
                with(products) {
                    size shouldBe 2
                    map { it.id } shouldBe listOf(Option.just("1"), Option.just("2"))
                }
            }
        }
    }

    @Test
    fun `when parsing correct empty product JSON should return correct DTO`() {
        "/products-empty.json".resourceToProductsDto { productsDto ->
            productsDto.products.fold({ shouldNotBeCalled() }) {
                it.shouldBeEmpty()
            }
        }
    }

    @Test
    fun `when parsing correct null product JSON should return correct DTO`() {
        "/products-null.json".resourceToProductsDto { productsDto ->
            productsDto.products.shouldBeNone()
        }
    }

    @Test
    fun `when parsing product JSON with missing fields should return correct DTO`() {
        "/products-missing-fields.json".resourceToProductsDto { productsDto ->
            productsDto.products.fold({ shouldNotBeCalled() }) { products ->
                with (products) {
                    size shouldBe 2
                    map { it.id } shouldBe listOf(Option.empty(), Option.just("2"))
                    map { it.type } shouldBe listOf(Option.just("chair"), Option.empty())
                }
            }
        }
    }

    @Test
    fun `when parsing product JSON with null fields should return correct DTO`() {
        "/products-null-fields.json".resourceToProductsDto { productsDto ->
            productsDto.products.fold({ shouldNotBeCalled() }) { products ->
                with (products) {
                    size shouldBe 2
                    map { it.name } shouldBe listOf(Option.empty(), Option.just("Lidhult"))
                    map { product -> product.price.flatMap { it.value } } shouldBe
                            listOf(Option.just(499), Option.empty())
                }
            }
        }
    }

    private fun String.resourceToProductsDto(block: (ProductsDto) -> Unit) {
        javaClass.getResourceAsStream(this)?.use { jsonInputStream ->
            jsonInputStream.reader().use { jsonReader ->
                val productsDto = get<Gson>().fromJson(jsonReader, ProductsDto::class.java)
                block(productsDto)
            }
        } ?: shouldNotBeCalled()
    }
}