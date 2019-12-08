package org.m0skit0.android.ikeachallenge.view.product.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.m0skit0.android.ikeachallenge.R
import org.m0skit0.android.ikeachallenge.util.view.BaseFragment

internal class ProductGridFragment : BaseFragment() {

    override val viewModel: ProductListingViewModel by currentScope.viewModel(this)

    private lateinit var productGridRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductGridAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_product_grid, container, false).apply {
            productGridRecyclerView = findViewById<RecyclerView>(R.id.recyclerProducts).apply {
                productAdapter = ProductGridAdapter(findNavController())
                adapter = productAdapter
            }
        }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.productOverviewList.observe({ lifecycle }) { products ->
            productAdapter.products = products
        }
    }
}
