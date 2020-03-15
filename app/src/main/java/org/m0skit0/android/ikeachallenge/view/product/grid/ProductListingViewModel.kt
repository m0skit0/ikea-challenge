package org.m0skit0.android.ikeachallenge.view.product.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.di.UseCaseModule.NAMED_GET_PRODUCTS
import org.m0skit0.android.ikeachallenge.di.ViewModelModule.NAMED_MUTABLE_LIST_PRODUCTS
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.util.view.BaseViewModel

internal class ProductListingViewModel : BaseViewModel() {

    private val getProductsUseCase: () -> Either<Throwable, List<Product>> by inject(NAMED_GET_PRODUCTS)

    private val mutableProductOverviewList: MutableLiveData<List<ProductOverview>> by inject(NAMED_MUTABLE_LIST_PRODUCTS)
    val productOverviewList: LiveData<List<ProductOverview>> by lazy {
        getProducts()
        mutableProductOverviewList
    }

    private fun getProducts() {
        launchWithLoading {
            getProductsUseCase().getOrPostError {
                mutableProductOverviewList.postValue(it.toProductOverviews())
            }
        }
    }
}
