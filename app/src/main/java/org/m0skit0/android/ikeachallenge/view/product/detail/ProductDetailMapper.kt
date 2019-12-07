package org.m0skit0.android.ikeachallenge.view.product.detail

import arrow.core.Either
import arrow.core.Option
import org.m0skit0.android.ikeachallenge.R
import org.m0skit0.android.ikeachallenge.data.api.PriceDto
import org.m0skit0.android.ikeachallenge.domain.ChairInfo
import org.m0skit0.android.ikeachallenge.domain.CouchInfo
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.domain.ProductInfo
import org.m0skit0.android.ikeachallenge.error.InvalidProduct
import org.m0skit0.android.ikeachallenge.util.getStringResource

internal fun Product.toDetail(): Either<Throwable, ProductDetail> =
    id.flatMap { id ->
        name.map { name ->
            val price = price.fold({ "???" }) { it.toPriceOverview() }
            val info = info.fold({ mapOf<String, String>() }) { it.toDetailInfo() }
            ProductDetail(id, name, price, imageUrl, info)
        }
    }.toEither { InvalidProduct("Product is invalid") }

private fun PriceDto.toPriceOverview() = "${value.toPriceOverview()} ${currency.toCurrencyOverview()}"
private fun Option<Double>.toPriceOverview() = fold({ "???" }) { "%.2f".format(it) }
private fun Option<String>.toCurrencyOverview() = fold({ "???" }) { it }

private fun ProductInfo.toDetailInfo(): Map<String, String> =
    when(this) {
        is ChairInfo -> toDetailInfo()
        is CouchInfo -> toDetailInfo()
    }

private fun ChairInfo.toDetailInfo(): Map<String, String> =
    (listOf<Option<Pair<String, String>>>() + material.toMaterialDetail() + color.toColorDetail())
        .mapNotNull { it.orNull() }
        .associate { it }

private fun CouchInfo.toDetailInfo(): Map<String, String> =
    (listOf<Option<Pair<String, String>>>() + numberOfSeats.toNumberOfSeatsDetail() + color.toColorDetail())
        .mapNotNull { it.orNull() }
        .associate { it }

private fun Option<String>.toMaterialDetail(): Option<Pair<String, String>> =
    map { getStringResource(R.string.material_title) to it }

private fun Option<String>.toNumberOfSeatsDetail(): Option<Pair<String, String>> =
    map { getStringResource(R.string.numberOfSeats_title) to it }

private fun Option<String>.toColorDetail(): Option<Pair<String, String>> =
    map { getStringResource(R.string.color_title) to it }