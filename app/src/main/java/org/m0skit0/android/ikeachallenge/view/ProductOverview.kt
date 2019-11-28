package org.m0skit0.android.ikeachallenge.view

import arrow.core.Option

internal data class ProductOverview(
    private val id: String,
    val name: String,
    val imageUrl: Option<String>,
    val price: String
)