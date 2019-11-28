package org.m0skit0.android.ikeachallenge.view

import org.m0skit0.android.ikeachallenge.data.api.PriceDto
import org.m0skit0.android.ikeachallenge.domain.Product

internal fun List<Product>.toProductOverviews(): List<ProductOverview> = mapNotNull { it.toProductOverview() }

internal fun Product.toProductOverview(): ProductOverview? {
    val id = id.orNull() ?: return null
    val name = name.orNull() ?: return null
    val price = price.orNull()?.toPriceOverview() ?: return null
    return ProductOverview(id, name, imageUrl, price)
}

internal fun PriceDto.toPriceOverview(): String = "$value $currency"

