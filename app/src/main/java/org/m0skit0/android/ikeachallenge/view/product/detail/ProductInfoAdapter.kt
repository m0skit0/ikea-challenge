package org.m0skit0.android.ikeachallenge.view.product.detail

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class ProductInfoAdapter : RecyclerView.Adapter<ProductInfoAdapter.ViewHolder>() {

    var info: List<Pair<String, String>> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(TextView(parent.context))

    override fun getItemCount(): Int = info.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textInfo.text = "${info[position].first}: ${info[position].second}"
    }

    class ViewHolder(itemView: TextView): RecyclerView.ViewHolder(itemView) {
        val textInfo: TextView = itemView
    }
}
