package org.m0skit0.android.ikeachallenge.data.api

import io.kotlintest.TestCase
import io.kotlintest.assertions.arrow.either.shouldBeLeft
import io.kotlintest.assertions.arrow.either.shouldBeLeftOfType
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.KoinFreeSpec
import org.m0skit0.android.ikeachallenge.data.repository.ProductRepositoryImpl
import org.m0skit0.android.ikeachallenge.error.APIException
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class TestProductRepositoryImpl : KoinFreeSpec() {

    @MockK
    private lateinit var mockProductApi: ProductApi

    @MockK
    private lateinit var mockCall: Call<ProductsDto>

    @MockK
    private lateinit var mockResponse: Response<ProductsDto>

    override val module = module {
        single { mockProductApi }
    }

    override fun beforeTest(testCase: TestCase) {
        MockKAnnotations.init(this)
        super.beforeTest(testCase)
    }

    init {
        "when call to API success should return valid ProductsDTO" {
            every { mockProductApi.getProducts() } returns mockCall
            every { mockCall.execute() } returns mockResponse
            every { mockResponse.isSuccessful } returns true
            every { mockResponse.body() } returns ProductsDto()

            ProductRepositoryImpl().getProducts().attempt().unsafeRunSync()
                .shouldBeRight(emptyList())
        }

        "when call to API body is null should return error" {
            every { mockProductApi.getProducts() } returns mockCall
            every { mockCall.execute() } returns mockResponse
            every { mockResponse.isSuccessful } returns true
            every { mockResponse.body() } returns null

            ProductRepositoryImpl().getProducts().attempt().unsafeRunSync()
                .shouldBeLeftOfType<APIException>()
        }

        "when call to API not success should return error" {
            every { mockProductApi.getProducts() } returns mockCall
            every { mockCall.execute() } returns mockResponse
            every { mockResponse.isSuccessful } returns false
            every { mockResponse.errorBody() } returns null

            ProductRepositoryImpl().getProducts().attempt().unsafeRunSync()
                .shouldBeLeftOfType<APIException>()
        }

        "when call to API error should return error" {
            every { mockProductApi.getProducts() } returns mockCall
            every { mockCall.execute() } throws IOException()

            ProductRepositoryImpl().getProducts().attempt().unsafeRunSync()
                .shouldBeLeftOfType<IOException>()
        }
    }
}