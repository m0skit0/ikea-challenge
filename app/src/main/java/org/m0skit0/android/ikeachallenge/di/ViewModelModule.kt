package org.m0skit0.android.ikeachallenge.di

import androidx.lifecycle.MutableLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.m0skit0.android.ikeachallenge.view.product.detail.ProductDetailFragment
import org.m0skit0.android.ikeachallenge.view.product.detail.ProductDetailViewModel
import org.m0skit0.android.ikeachallenge.view.product.grid.ProductGridFragment
import org.m0skit0.android.ikeachallenge.view.product.grid.ProductListingViewModel
import org.m0skit0.android.ikeachallenge.view.product.grid.ProductOverview
import org.koin.dsl.module

internal object ViewModelModule : KoinModuleProvider {

    val NAMED_MUTABLE_LIST_PRODUCTS = named("NAMED_MUTABLE_LIST_PRODUCTS")
    val NAMED_MUTABLE_PRODUCT = named("NAMED_MUTABLE_PRODUCT")
    val NAMED_MUTABLE_BOOLEAN = named("NAMED_MUTABLE_BOOLEAN")
    val NAMED_MUTABLE_ERROR = named("NAMED_MUTABLE_ERROR")

    override fun module(): Module  = module {
        factory(NAMED_MUTABLE_LIST_PRODUCTS) { MutableLiveData<List<ProductOverview>>() }
        factory(NAMED_MUTABLE_PRODUCT) { MutableLiveData<ProductOverview>() }

        factory(NAMED_MUTABLE_BOOLEAN) { MutableLiveData<Boolean>() }
        factory(NAMED_MUTABLE_ERROR) { MutableLiveData<Throwable>() }

        scope(named<ProductGridFragment>()) {
            viewModel { ProductListingViewModel() }
        }

        scope(named<ProductDetailFragment>()) {
            viewModel { (id: String) -> ProductDetailViewModel(id) }
        }
    }
}
