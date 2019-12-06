package org.m0skit0.android.ikeachallenge.view.product.grid

import arrow.core.Option

internal data class ProductOverview(
    val id: String,
    val name: String,
    val imageUrl: Option<String>,
    val price: String
)