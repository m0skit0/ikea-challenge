package org.m0skit0.android.ikeachallenge.view.product.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.m0skit0.android.ikeachallenge.R


internal class ProductInfoAdapter : RecyclerView.Adapter<ProductInfoAdapter.ViewHolder>() {

    var info: List<Pair<String, String>> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater.from(parent.context).inflate(R.layout.item_product_info, parent, false).run {
            ViewHolder(this)
        }

    override fun getItemCount(): Int = info.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(info[position]) {
            holder.textInfo.text = "${info()}: ${value()}"
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textInfo: TextView = itemView.findViewById(R.id.textInfoItem)
    }
}
