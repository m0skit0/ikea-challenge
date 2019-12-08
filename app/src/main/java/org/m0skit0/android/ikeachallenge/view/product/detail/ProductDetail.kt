package org.m0skit0.android.ikeachallenge.view.product.detail

import arrow.core.Option

internal typealias ItemInfo = Pair<String, String>
internal fun ItemInfo.info() = first
internal fun ItemInfo.value() = second

internal data class ProductDetail(
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: Option<String>,
    val info: List<ItemInfo>
)
