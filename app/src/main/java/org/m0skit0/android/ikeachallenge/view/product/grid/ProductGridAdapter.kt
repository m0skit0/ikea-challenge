package org.m0skit0.android.ikeachallenge.view.product.grid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.m0skit0.android.ikeachallenge.R
import org.m0skit0.android.ikeachallenge.view.product.detail.ProductDetailFragment

internal class ProductGridAdapter(private val navController: NavController) : RecyclerView.Adapter<ProductGridAdapter.ViewHolder>() {

    var products: List<ProductOverview> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater.from(parent.context).inflate(R.layout.item_product_overview, parent, false).run {
            ViewHolder(this)
        }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder) {
            products[position].let { product ->

                Picasso.get().run {
                    product.imageUrl.fold({
                        load(R.drawable.ic_empty_image)
                    }) { url ->
                        load(url)
                    }
                }.error(R.drawable.ic_empty_image).into(image)

                name.text = product.name
                price.text = product.price

                itemView.setOnClickListener {
                    // TODO Find out why back button is not working correctly
                    //  for the second time this navigation is called
                    val bundle = ProductDetailFragment.bundle(product.id)
                    navController.navigate(R.id.productDetailFragment, bundle)
                }
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageProduct)
        val name: TextView = itemView.findViewById(R.id.textProductName)
        val price: TextView = itemView.findViewById(R.id.textProductPrice)
    }
}