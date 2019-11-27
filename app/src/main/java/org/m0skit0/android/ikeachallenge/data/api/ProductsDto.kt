package org.m0skit0.android.ikeachallenge.data.api

import arrow.core.Option

internal data class ProductsDto(
    val products: Option<List<ProductDto>> = Option.empty()
)

internal data class ProductDto(
    val id: Option<String> = Option.empty(),
    val imageUrl: Option<String> = Option.empty(),
    val info: Option<Map<String, String>> = Option.empty(),
    val name: Option<String> = Option.empty(),
    val price: Option<PriceDto> = Option.empty(),
    val type: Option<String> = Option.empty()
)

internal data class PriceDto(
    val currency: Option<String> = Option.empty(),
    val value: Option<Int> = Option.empty()
)