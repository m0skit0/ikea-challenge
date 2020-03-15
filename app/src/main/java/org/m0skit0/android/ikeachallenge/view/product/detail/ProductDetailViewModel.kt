package org.m0skit0.android.ikeachallenge.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.di.ViewModelModule.NAMED_MUTABLE_PRODUCT
import org.m0skit0.android.ikeachallenge.usecase.GetProduct
import org.m0skit0.android.ikeachallenge.util.view.BaseViewModel

internal class ProductDetailViewModel(private val productId: String) : BaseViewModel() {

    private val getProductUseCase: GetProduct by inject()

    private val mutableProductDetail: MutableLiveData<ProductDetail> by inject(NAMED_MUTABLE_PRODUCT)
    val productDetail: LiveData<ProductDetail> by lazy {
        getProduct()
        mutableProductDetail
    }

    private fun getProduct() {
        launch {
            getProductUseCase(productId).getOrPostError { product ->
                product.toDetail().getOrPostError {
                    mutableProductDetail.postValue(it)
                }
            }
        }
    }
}
