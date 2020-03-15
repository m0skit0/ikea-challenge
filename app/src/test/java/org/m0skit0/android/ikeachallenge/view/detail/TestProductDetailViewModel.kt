package org.m0skit0.android.ikeachallenge.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import io.kotlintest.TestCase
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.matchers.types.shouldNotBeNull
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.koin.core.module.Module
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.KoinFreeSpec
import org.m0skit0.android.ikeachallenge.data.api.PriceDto
import org.m0skit0.android.ikeachallenge.di.UseCaseModule.NAMED_GET_PRODUCT
import org.m0skit0.android.ikeachallenge.di.UseCaseModule.NAMED_GET_PRODUCTS
import org.m0skit0.android.ikeachallenge.di.ViewModelModule.NAMED_MUTABLE_BOOLEAN
import org.m0skit0.android.ikeachallenge.di.ViewModelModule.NAMED_MUTABLE_ERROR
import org.m0skit0.android.ikeachallenge.di.ViewModelModule.NAMED_MUTABLE_LIST_PRODUCTS
import org.m0skit0.android.ikeachallenge.di.ViewModelModule.NAMED_MUTABLE_PRODUCT
import org.m0skit0.android.ikeachallenge.domain.ChairInfo
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.usecase.product
import org.m0skit0.android.ikeachallenge.usecase.products
import org.m0skit0.android.ikeachallenge.util.getStringResource
import org.m0skit0.android.ikeachallenge.view.product.detail.ProductDetail
import org.m0skit0.android.ikeachallenge.view.product.detail.ProductDetailViewModel
import org.m0skit0.android.ikeachallenge.view.product.detail.toDetail
import org.m0skit0.android.ikeachallenge.view.product.grid.ProductListingViewModel
import org.m0skit0.android.ikeachallenge.view.product.grid.ProductOverview
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TestProductDetailViewModel : KoinFreeSpec() {

    @MockK
    private lateinit var mockMutableLoading: MutableLiveData<Boolean>

    @MockK
    private lateinit var mockMutableError: MutableLiveData<Throwable>

    @MockK
    private lateinit var mockMutableProductDetail: MutableLiveData<ProductDetail>

    @MockK
    private lateinit var mockMutableListProducts: MutableLiveData<List<ProductOverview>>

    @MockK
    private lateinit var mockProduct: Product

    @MockK
    private lateinit var mockChairInfo: ChairInfo

    override val module: Module = module {
        single(NAMED_MUTABLE_PRODUCT) { mockMutableProductDetail }
        single(NAMED_MUTABLE_BOOLEAN) { mockMutableLoading }
        single(NAMED_MUTABLE_ERROR) { mockMutableError }
        single(NAMED_MUTABLE_LIST_PRODUCTS) { mockMutableListProducts }
        single<() -> Either<Throwable, List<Product>>>(NAMED_GET_PRODUCTS) { ::products }
        single<(String) -> Either<Throwable, Product>>(NAMED_GET_PRODUCT) { ::product }
    }

    override fun beforeTest(testCase: TestCase) {
        MockKAnnotations.init(this)

        mockkStatic("org.m0skit0.android.ikeachallenge.util.KoinUtilsKt")
        every { getStringResource(any()) } returns "resource"

        every { mockProduct.id } returns "id".toOption()
        every { mockProduct.name } returns "name".toOption()
        every { mockProduct.price } returns PriceDto("kr".toOption(), 0.0.toOption()).toOption()
        every { mockProduct.imageUrl } returns "url".toOption()
        every { mockProduct.info } returns mockChairInfo.toOption()
        every { mockChairInfo.material } returns "material".toOption()
        every { mockChairInfo.color } returns "color".toOption()

        mockkStatic("org.m0skit0.android.ikeachallenge.usecase.ProductUseCasesKt")
        coEvery { product(any()) } returns mockProduct.right()
        coEvery { products() } returns emptyList<Product>().right()

        every { mockMutableProductDetail.observeForever(any()) } answers {
            mockProduct.toDetail().fold({
                throw it
            }) {
                firstArg<Observer<ProductDetail>>().onChanged(it)
            }
        }
        super.beforeTest(testCase)
    }

    init {

        "when observing product overview list should start fetch product overview and return fetched list" {
            var isPostedDetail = false
            every { mockMutableProductDetail.postValue(any()) } answers {
                isPostedDetail = true
            }

            var isLoadingTrueCalled = false
            var isLoadingFalseCalled = false
            runBlocking {
                suspendCoroutine<Unit> { cont ->
                    every { mockMutableLoading.postValue(any()) } answers {
                        if (firstArg()) isLoadingTrueCalled = true else {
                            isLoadingFalseCalled = true
                            cont.resume(Unit)
                        }
                    }
                    ProductDetailViewModel("id").productDetail.observeForever { detail ->
                        isLoadingTrueCalled.shouldBeTrue()
                        detail.shouldNotBeNull()
                    }
                }
            }
            isLoadingFalseCalled.shouldBeTrue()
            isPostedDetail.shouldBeTrue()
        }

        "when use case returns error should remove loading and set error live data with error" {
            var isLoadingTrueCalled = false
            var isLoadingFalseCalled = false

            var throwable: Throwable? = null
            coEvery { product("id") } returns IllegalArgumentException().left()
            runBlocking {
                suspendCoroutine<Unit> { cont ->
                    every { mockMutableLoading.postValue(any()) } answers {
                        if (firstArg()) isLoadingTrueCalled = true else {
                            isLoadingFalseCalled = true
                            cont.resume(Unit)
                        }
                    }
                    every { mockMutableError.postValue(any()) } answers {
                        throwable = firstArg()
                    }
                    ProductDetailViewModel("id").productDetail
                }
            }
            isLoadingTrueCalled.shouldBeTrue()
            isLoadingFalseCalled.shouldBeTrue()
            throwable.shouldBeTypeOf<IllegalArgumentException>()
        }
    }
}
