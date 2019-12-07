package org.m0skit0.android.ikeachallenge.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.di.NAMED_MUTABLE_PRODUCT
import org.m0skit0.android.ikeachallenge.usecase.GetProductUseCase
import org.m0skit0.android.ikeachallenge.view.BaseViewModel

internal class ProductDetailViewModel(val productId: String) : BaseViewModel() {

    private val getProductUseCase: GetProductUseCase by inject()

    private val mutableProductDetail: MutableLiveData<ProductDetail> by inject(NAMED_MUTABLE_PRODUCT)
    val productDetail: LiveData<ProductDetail> by lazy {
        getProduct()
        mutableProductDetail
    }

    private fun getProduct() {
        launchWithLoading {
            getProductUseCase(productId).getOrPostError { product ->
                product.toDetail().getOrPostError {
                    mutableProductDetail.postValue(it)
                }
            }
        }
    }
}