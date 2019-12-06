package org.m0skit0.android.ikeachallenge.view.product.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.di.NAMED_MUTABLE_BOOLEAN
import org.m0skit0.android.ikeachallenge.di.NAMED_MUTABLE_ERROR
import org.m0skit0.android.ikeachallenge.di.NAMED_MUTABLE_LIST_PRODUCTS
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.usecase.GetProductsUseCase

internal class ProductListingViewModel : ViewModel(), CoroutineScope by CoroutineScope(Dispatchers.IO), KoinComponent {

    private val getProductsUseCase: GetProductsUseCase by inject()
    private var productList: List<Product> = emptyList()

    private val mutableProductOverviewList: MutableLiveData<List<ProductOverview>> by inject(NAMED_MUTABLE_LIST_PRODUCTS)
    val productOverviewList: LiveData<List<ProductOverview>> by lazy {
        getProducts()
        mutableProductOverviewList
    }

    private val mutableIsLoading: MutableLiveData<Boolean> by inject(NAMED_MUTABLE_BOOLEAN)
    val isLoading: LiveData<Boolean> by lazy { mutableIsLoading }

    private val mutableError: MutableLiveData<Throwable> by inject(NAMED_MUTABLE_ERROR)
    val error: LiveData<Throwable> by lazy { mutableError }

    private fun getProducts() {
        mutableIsLoading.postValue(true)
        launch {
            getProductsUseCase().fold({
                mutableError.postValue(it)
            }) {
                productList = it
                mutableProductOverviewList.postValue(it.toProductOverviews())
            }
            mutableIsLoading.postValue(false)
        }
    }
}