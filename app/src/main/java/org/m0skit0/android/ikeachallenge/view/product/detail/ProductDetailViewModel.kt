package org.m0skit0.android.ikeachallenge.view.product.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.usecase.GetProductUseCase

internal class ProductDetailViewModel(val productId: String) : ViewModel(), CoroutineScope by CoroutineScope(Dispatchers.IO), KoinComponent {

    private val getProductUseCase: GetProductUseCase by inject()


}