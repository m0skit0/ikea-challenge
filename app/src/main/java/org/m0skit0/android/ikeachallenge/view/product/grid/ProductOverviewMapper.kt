package org.m0skit0.android.ikeachallenge.view.product.grid

import arrow.core.Option
import org.m0skit0.android.ikeachallenge.data.api.PriceDto
import org.m0skit0.android.ikeachallenge.domain.Product

internal fun List<Product>.toProductOverviews(): List<ProductOverview> = mapNotNull { it.toProductOverview() }

private fun Product.toProductOverview(): ProductOverview? {
    val id = id.orNull() ?: return null
    val name = name.orNull() ?: return null
    val price = price.orNull()?.toPriceOverview() ?: return null
    return ProductOverview(id, name, imageUrl, price)
}

private fun PriceDto.toPriceOverview() = "${value.toPriceOverview()} ${currency.toCurrencyOverview()}"
private fun Option<Double>.toPriceOverview() = fold({ "???" }) { "%.2f".format(it) }
private fun Option<String>.toCurrencyOverview() = fold({ "???" }) { it }

