package org.m0skit0.android.ikeachallenge.view.product.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.m0skit0.android.ikeachallenge.R
import org.m0skit0.android.ikeachallenge.view.BaseFragment

internal class ProductGridFragment : BaseFragment() {

    override val viewModel: ProductListingViewModel by currentScope.viewModel(this)

    private lateinit var productGridRecyclerView: RecyclerView
    private val productAdapter = ProductGridAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_product_grid, container, false).apply {
            productGridRecyclerView = findViewById<RecyclerView>(R.id.recycler_products).apply {
                adapter = productAdapter
            }
        }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        with(viewModel) {
            productOverviewList.observe({ lifecycle }) { products ->
                productAdapter.products = products
            }
        }
    }
}