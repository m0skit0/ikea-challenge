package org.m0skit0.android.ikeachallenge.view.product.detail

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import org.m0skit0.android.ikeachallenge.R
import org.m0skit0.android.ikeachallenge.util.toast
import org.m0skit0.android.ikeachallenge.view.BaseDialogFragment

internal class ProductDetailFragment : BaseDialogFragment() {

    companion object {
        private val KEY_ID = "id"
        fun bundle(id: String): Bundle = bundleOf(KEY_ID to id)
    }

    override val viewModel: ProductDetailViewModel by lazy {
        val id = arguments?.get(KEY_ID)
        currentScope.getViewModel<ProductDetailViewModel>(this@ProductDetailFragment) {
            parametersOf(id)
        }
    }

    override val layout: Int = R.layout.fragment_product_detail

    private lateinit var productImageView: ImageView
    private lateinit var productName: TextView
    private lateinit var productPrice: TextView
    private lateinit var productInfoRecyclerView: RecyclerView
    private val productInfoAdapter = ProductInfoAdapter()
    private lateinit var productAddToCart: Button

    override fun View.initialize() {
        productImageView = findViewById(R.id.imageProduct)
        productName = findViewById(R.id.textName)
        productPrice = findViewById(R.id.textPrice)
        productInfoRecyclerView = findViewById<RecyclerView>(R.id.recyclerInfo).apply {
            adapter = productInfoAdapter
        }
        productAddToCart = findViewById<Button>(R.id.buttonAdd).apply {
            // TODO Actually add the item to the cart
            setOnClickListener { toast("Add clicked") }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.productDetail.observe({ lifecycle  }) { productDetail ->
            with (productDetail) {
                Picasso.get().run {
                    imageUrl.fold({
                        load(R.drawable.ic_empty_image)
                    }) { url ->
                        load(url)
                    }
                }.error(R.drawable.ic_empty_image).into(productImageView)
                productName.text = name
                productPrice.text = price
                productInfoAdapter.info = info.toList()
            }
        }
    }
}
