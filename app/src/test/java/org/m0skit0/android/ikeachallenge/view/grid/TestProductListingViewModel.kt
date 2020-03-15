package org.m0skit0.android.ikeachallenge.view.grid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.kotlintest.TestCase
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.koin.core.module.Module
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.KoinFreeSpec
import org.m0skit0.android.ikeachallenge.di.UseCaseModule.NAMED_GET_PRODUCTS
import org.m0skit0.android.ikeachallenge.di.ViewModelModule.NAMED_MUTABLE_BOOLEAN
import org.m0skit0.android.ikeachallenge.di.ViewModelModule.NAMED_MUTABLE_ERROR
import org.m0skit0.android.ikeachallenge.di.ViewModelModule.NAMED_MUTABLE_LIST_PRODUCTS
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.usecase.products
import org.m0skit0.android.ikeachallenge.view.product.grid.ProductListingViewModel
import org.m0skit0.android.ikeachallenge.view.product.grid.ProductOverview
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TestProductListingViewModel : KoinFreeSpec() {

    @MockK
    private lateinit var mockMutableListProductOverview: MutableLiveData<List<ProductOverview>>

    @MockK
    private lateinit var mockMutableLoading: MutableLiveData<Boolean>

    @MockK
    private lateinit var mockMutableError: MutableLiveData<Throwable>

    override val module: Module = module {
        single(NAMED_MUTABLE_LIST_PRODUCTS) { mockMutableListProductOverview }
        single(NAMED_MUTABLE_BOOLEAN) { mockMutableLoading }
        single(NAMED_MUTABLE_ERROR) { mockMutableError }
        single<() -> Either<Throwable, List<Product>>>(NAMED_GET_PRODUCTS) { ::products }
    }

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        MockKAnnotations.init(this)
        mockkStatic("org.m0skit0.android.ikeachallenge.usecase.ProductUseCasesKt")
        coEvery { products() } returns emptyList<Product>().right()
        every { mockMutableListProductOverview.observeForever(any()) } answers {
            firstArg<Observer<List<ProductOverview>>>().onChanged(emptyList())
        }
    }

    init {

        "when observing product overview list should start fetch product overview and return fetched list" {
            var isPostedList = false
            every { mockMutableListProductOverview.postValue(any()) } answers {
                isPostedList = true
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
                    ProductListingViewModel().productOverviewList.observeForever { list ->
                        isLoadingTrueCalled.shouldBeTrue()
                        list shouldBe emptyList()
                    }
                }
            }
            isLoadingFalseCalled.shouldBeTrue()
            isPostedList.shouldBeTrue()
        }

        "when use case returns error should remove loading and set error live data with error" {
            var isLoadingTrueCalled = false
            var isLoadingFalseCalled = false

            var throwable: Throwable? = null
            coEvery { products() } returns IllegalArgumentException().left()
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
                    ProductListingViewModel().productOverviewList
                }
            }
            isLoadingTrueCalled.shouldBeTrue()
            isLoadingFalseCalled.shouldBeTrue()
            throwable.shouldBeTypeOf<IllegalArgumentException>()
        }
    }
}
