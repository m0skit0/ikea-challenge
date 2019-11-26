package org.m0skit0.android.ikeachallenge.data.api

import arrow.core.Option

internal data class ProductsDto(
    val products: List<Product> = emptyList()
)

internal data class Product(
    val id: Option<String> = Option.empty(),
    val imageUrl: Option<String> = Option.empty(),
    val info: Map<String, String> = mapOf(),
    val name: Option<String> = Option.empty(),
    val price: Option<Price> = Option.empty(),
    val type: Option<String> = Option.empty()
)

internal data class Price(
    val currency: Option<String> = Option.empty(),
    val value: Option<Int> = Option.empty()
)