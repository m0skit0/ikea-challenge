package org.m0skit0.android.ikeachallenge.view.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.m0skit0.android.ikeachallenge.R
import org.m0skit0.android.ikeachallenge.view.ErrorDialogFragment

internal class ProductGridFragment : Fragment() {

    private val viewModel: ProductListingViewModel by currentScope.viewModel(this)

    private lateinit var productGridRecyclerView: RecyclerView
    private val productAdapter = ProductGridAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_product_grid, container).apply {
            productGridRecyclerView = findViewById<RecyclerView>(R.id.recycler_products).apply {
                adapter = productAdapter
            }
        }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            isLoading.observe({ lifecycle }) { isLoading ->
                if (isLoading) showLoading() else hideLoading()
            }
            error.observe({ lifecycle }) { error ->
                showError(error)
            }
            productOverviewList.observe({ lifecycle }) { products ->
                productAdapter.products = products
            }
        }
    }

    private fun showLoading() {
        findNavController().navigate(R.id.loadingDialogFragment)
    }

    private fun hideLoading() {
        findNavController().popBackStack()
    }

    private fun showError(error: Throwable) {
        val bundle =
            ErrorDialogFragment.bundle(error)
        findNavController().navigate(R.id.errorDialogFragment, bundle)
    }
}