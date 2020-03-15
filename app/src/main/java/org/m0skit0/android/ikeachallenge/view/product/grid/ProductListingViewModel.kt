package org.m0skit0.android.ikeachallenge.view.product.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.di.NAMED_MUTABLE_LIST_PRODUCTS
import org.m0skit0.android.ikeachallenge.usecase.GetProducts
import org.m0skit0.android.ikeachallenge.util.view.BaseViewModel

internal class ProductListingViewModel : BaseViewModel() {

    private val getProductsUseCase: GetProducts by inject()

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